package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateDTO
import com.telemondo.qs.repository.CounterRepository
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.repository.QueueUserRepository
import com.telemondo.qs.utils.mapper.CounterMapper
import com.telemondo.qs.web.controller.CounterController.CounterFilter
import io.nats.client.Connection
import jakarta.transaction.Transactional
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class CounterServiceImpl(
    private val counterRepository: CounterRepository,
    private val counterMapper: CounterMapper = Mappers.getMapper(CounterMapper::class.java),
    private val counterTypeRepository: CounterTypeRepository,
    private val queueUserRepository: QueueUserRepository,
    private val natsConnection: Connection
): CounterService {

    @Transactional
    override fun getCounters(counterFilter: CounterFilter): List<CounterDTO> {
        val counters = counterRepository.findDynamicCounters(counterFilter)
        println(counterFilter.pageSize)
        if (counters.count() == 0) {
                throw Exception("No counters yet.")
            }
        return counters.map {
                counterMapper.toDomain(it)
            }
    }

    @Transactional
    override fun getCounter(id: String): CounterDTO {
        val counter = counterRepository.findById(id)

        if(counter.isEmpty){
            throw Exception("Counter with id $id does not exist.")
        }

        return counterMapper.toDomain(counter.get())
    }

    //    we made counterCreateDTO WITHOUT id because counterDTO has id required.
    @Transactional
    override fun createCounter(counterCreateDTO: CounterCreateDTO): CounterDTO {

        val counter = counterMapper.mapCreateToEntity(counterCreateDTO)

        val counterType = counterTypeRepository.findById(counterCreateDTO.counterTypeId).orElseThrow{Exception("Counter type with id ${counterCreateDTO.counterTypeId} does not exist.")}

        counter.counterType = counterType

        val numOfExistingCounters = counterRepository.countByCounterTypeId(counterType.id).toInt() + 1

        val counterName = "${counterType.counterName}-$numOfExistingCounters"

        counter.name = counterName

        counterRepository.save(counter)

        return counterMapper.toDomain(counter)
    }

    @Transactional
    override fun updateCounter(counterUpdateDTO: CounterUpdateDTO): CounterDTO {

        val counter = counterRepository.findById(counterUpdateDTO.id)

        if (counter.isEmpty) {
            throw Exception("Counter with ID ${counterUpdateDTO.id} does not exist.")
        }

        val counterEntity = counterMapper.mapUpdateToEntity(counterUpdateDTO, counter.get())

        counterRepository.save(counterEntity)

        return counterMapper.toDomain(counterEntity)
    }

    @Transactional
    override fun counterNextCustomer(id: String, customerType: Int) {
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}

        if(counter.currentCustomer?.status == 2 || counter.status != 1){
            throw Exception("Please mark the current customer as Finished/No-show before calling next customer.")
        }

//        setting of the benchmark datetime to 12am of the current day to be the reference for the narrowing down
//        of queueUsers in the database so that only the customers of current day are shown
        val time12AM = "00:00:00"
        val currentDate = LocalDate.now().toString()
        val currentDateTime12AM = currentDate + "T" + time12AM
        val ldt = LocalDateTime.parse(currentDateTime12AM)
//        conversion of LocalDateTime to Instant
        val instant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        val status = 1

//        sql query that retrieves queueUsers that have status = 1 (waiting) after createdAt = "the benchmark datetime" with dynamic counterType and customerType filters
//        in ascending order according to ticket number
        val queueUsersList = queueUserRepository.findByCounterTypeIdAndCustomerTypeAndStatusAndCreatedAtAfterOrderByTicketNumAsc(counter.counterType.id, customerType, status, instant)

//        exception if there are no customers yet/anymore
        if(queueUsersList.count() == 0) {
//            let the status stay at receiving if there are no more next in line
//            counter.status = 1
            println("There are no more customers in line.")
            println("counter status: " + counter.status)
            throw Exception("There are no customers in line.")
        }
//        conversion of all elements of the list to their respective ticketNum only
        val queueUsersListTicketNumbers = queueUsersList.map {it.ticketNum}

//        setting of currentCustomer being entertained by counter
        val currentCustomer = queueUsersList[0]

        counter.currentCustomer = currentCustomer

//        populate counter property of current customer
        currentCustomer.counter = counter
        counter.currentCustomer?.status = 2

//        define currentCustomer's entertainedAt timestamp
        currentCustomer.entertainedAt = Instant.now()

//        change the status into entertaining
        counter.status = 2

        println(queueUsersList)
        println(queueUsersListTicketNumbers)
        println("Current Customer: " + currentCustomer.ticketNum)
        println("Counter Status: " + counter.status)

        val message = getCounterScreens()
//        Create a serializer for Map<String, String>
        val serializer = MapSerializer(String.serializer(), String.serializer())
//        Convert Map into a format that can be represented by bytes
        val jsonString = kotlinx.serialization.json.Json.encodeToString(serializer, message)
        natsConnection.publish("screen-counters", jsonString.toByteArray())
    }

    @Transactional
    override fun noShowCounterCustomer(id: String){
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}
        if(counter.currentCustomer?.status == null){
            throw Exception("There is no customer at the counter currently.")
        }
//        set the status of the customer to no-show
//        used safe call operator to prevent exceptions since currentCustomer is a nullable value
        counter.currentCustomer?.status = -1
//        change the status of the counter to a receiving status after completing a customer
        counter.status = 1
        println("${counter.currentCustomer?.ticketNum} is marked as no-show.")
//        remove the currentCustomer value from counterDTO to make sure it's not falsely populated
//        during the waiting time when there's no more people in line, preventing foreign key constraints
//        in case of deleting queueUsers who happen to be falsely linked to a counter that hasn't cleared him/her yet
        counter.currentCustomer = null
    }

    @Transactional
    override fun finishCounterCustomer(id: String){
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}
        if(counter.currentCustomer?.status == null) {
            throw Exception("There is no customer at the counter currently.")
        }
//        set the status of the customer to complete
//        used safe call operator to prevent exceptions since currentCustomer is a nullable value
        counter.currentCustomer?.status = 3
//      change the status of the counter to a receiving status after completing a customer
        counter.status = 1
        println("${counter.currentCustomer?.ticketNum} is marked as finished.")
//        remove the currentCustomer value from counterDTO to make sure it's not falsely populated
//        during the waiting time when there's no more people in line, preventing foreign key constraints
//        in case of deleting queueUsers who happen to be falsely linked to a counter that hasn't cleared him/her yet
        counter.currentCustomer = null
    }

    @Transactional
    override fun nextRegularCustomer(id: String) {
        counterNextCustomer(id, 1)
    }

    @Transactional
    override fun nextNonRegularCustomer(id: String) {
        counterNextCustomer(id, 2)
    }

    @Transactional
    override fun getCounterScreens(): Map<String, String> {
        val counters = counterRepository.findByStatusNotIn()

//        convert list into dictionary with counter as the key and currentCustomer as the value
//        associate receives a Pair object and we needed to use elvis operator for ticket num
        val counterMap = counters.associate { Pair(it.name, it.currentCustomer?.ticketNum ?: "-") }
        println(counterMap)
        return counterMap
    }


    @Transactional
    override fun pauseCounter(id: String) {
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}
        counter.status = 3
    }

    @Transactional
    override fun turnOffCounter(id: String) {
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}
        counter.status = -1
    }

    @Transactional
    override fun delCounter(id: String) {
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}
        counter.status = -3
    }

}

