package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateStatusDTO
import com.telemondo.qs.entity.Counter
import com.telemondo.qs.repository.CounterRepository
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.repository.QueueUserRepository
import com.telemondo.qs.utils.mapper.CounterMapper
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Size
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class CounterServiceImpl(
    private val counterRepository: CounterRepository,
    private val counterMapper: CounterMapper = Mappers.getMapper(CounterMapper::class.java),
    private val counterTypeRepository: CounterTypeRepository,
    private val queueUserRepository: QueueUserRepository
): CounterService {

    @Transactional
    override fun getCounters(startingPage: Int, pageSize: Int): List<CounterDTO> {
        val pageable: Pageable = PageRequest.of(startingPage, pageSize, Sort.by(Sort.Direction.ASC, "name"))
        val counters = counterRepository.findAll(pageable)
        if (counters.count() == 0) {
            throw Exception("No counters yet.")
        }

        return counters.content.map {
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

        counterRepository.save(counter)

        return counterMapper.toDomain(counter)
    }

    @Transactional
    override fun updateCounter(counterDTO: CounterDTO): CounterDTO {

        val counter = counterRepository.findById(counterDTO.id)

        if (counter.isEmpty) {
            throw Exception("Counter with ID ${counterDTO.id} does not exist.")
        }

        val counterEntity = counterMapper.mapUpdateToEntity(counterDTO, counter.get())

        counterRepository.save(counterEntity)

        return counterMapper.toDomain(counterEntity)
    }

    @Transactional
    override fun delCounter(id: String) {

        val exists = counterRepository.existsById(id)

        if (!exists) {
            throw Exception("Counter with ID $id does not exist.")
        }
        counterRepository.deleteById(id)
    }

    @Transactional
    override fun counterDoNextCustomer(id: String, customerType: Int) {
        val counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}

//        used safe call operator to prevent exceptions since currentCustomer is a nullable value
//        setting the currentCustomer's status to 3 so that they would not be waiting in line anymore
//        and will not be retrieved by the queueUsersList function
        counter.currentCustomer?.status = 3

//        remove the currentCustomer value from counterDTO to make sure it's not falsely populated
//        during the waiting time when there's no more people in line, preventing foreign key constraints
//        in case of deleting queueUsers who happen to be falsely linked to a counter that hasn't cleared him/her yet
        counter.currentCustomer = null

//        change the status of the counter to a receiving status after completing a customer
        counter.status = 1

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

//        change the status into entertaining
        counter.status = 2

        println(queueUsersList)
        println(queueUsersListTicketNumbers)
        println("Current Customer: " + currentCustomer.ticketNum)
        println("counter status: " + counter.status)

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

    //    alternative way to update
//    @Transactional
//    override fun updateStatus(id: String, status: Int): CounterDTO {
//
//        val counter = counterRepository.findById(id).orElseThrow{(Exception("Counter with ID $id does not exist."))}
//
//        counter.status = status
//
//        counterRepository.save(counter)
//
//        return counterMapper.toDomain(counter)
//    }

//    @Transactional
//    override fun updateStatus(counterUpdateStatusDTO: CounterUpdateStatusDTO): CounterDTO {
//
//        var counter = counterRepository.findById(counterUpdateStatusDTO.id)
//
//        if (counter.isEmpty) {
//            throw Exception("Counter with ID ${counterUpdateStatusDTO.id} does not exist.")
//        }
//
//        var savedEntity = counterMapper.mapUpdateStatusToEntity(counterUpdateStatusDTO, counter.get())
//
//
//        counterRepository.save(savedEntity)
//
//        return counterMapper.toDomain(savedEntity)
//    }
}

