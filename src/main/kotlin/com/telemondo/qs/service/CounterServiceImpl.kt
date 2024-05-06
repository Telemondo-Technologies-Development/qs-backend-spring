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
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers
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
    override fun getCounters(): List<CounterDTO> {

        val counters = counterRepository.findAll()

        if (counters.count() == 0) {
            throw Exception("No counters yet.")
        }

        return counters.map {
            counterMapper.toDomain(it)
        }
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

        counterRepository.save(counterMapper.toEntity(counterDTO))

//        much safer update function kay mas connected siya sa Mapstruct
//        in the "req" parameter which can be "CounterCreateDTO" or any DTO with missing properties, you can still map them to an EXISTING entity w/ full properties
//        fun mapUpdateToEntity(req: CounterDTO, @MappingTarget counter: Counter): Counter

        return counterMapper.toDomain(counter.get())
    }

    @Transactional
    override fun delCounter(id: String) {

        val exists = counterRepository.existsById(id)

        if (!exists) {
            throw Exception("Counter with ID $id does not exist.")
        }
        counterRepository.deleteById(id)
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

    @Transactional
    override fun updateStatus(counterUpdateStatusDTO: CounterUpdateStatusDTO): CounterDTO {

        var counter = counterRepository.findById(counterUpdateStatusDTO.id)

        if (counter.isEmpty) {
            throw Exception("Counter with ID ${counterUpdateStatusDTO.id} does not exist.")
        }

        var savedEntity = counterMapper.mapUpdateStatusToEntity(counterUpdateStatusDTO, counter.get())


        counterRepository.save(savedEntity)

        return counterMapper.toDomain(savedEntity)
    }

    @Transactional
    override fun counterDoNextCustomer(id: String) {
        var counter = counterRepository.findById(id).orElseThrow{Exception("Counter with id $id does not exist.")}


//        used safe call operator to prevent exceptions since currentCustomer is a nullable value
//        setting the currentCustomer's status to 3 so that they would not be waiting in line anymore
//        and will not be retrieved by the queueUsersList function
        counter.currentCustomer?.status = 3

//        setting of the benchmark datetime to 12am of the current day to be the reference for the narrowing down
//        of queueUsers in the database so that only the customers of current day are shown
        val time12AM = "00:00:00"
        val currentDate = LocalDate.now().toString()
        val currentDateTime12AM = currentDate + "T" + time12AM
        val ldt = LocalDateTime.parse(currentDateTime12AM)
//        conversion of LocalDateTime to Instant
        val instant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        val status = 1

//        sql query that retrieves queueUsers that have status = 1 (waiting) after createdAt = "the benchmark datetime"
//        in ascending order according to ticket number
        var queueUsersList = queueUserRepository.findByStatusAndCreatedAtAfterOrderByTicketNumAsc(status, instant)

//        exception if there are no customers yet/anymore
        if(queueUsersList.count() == 0) {
            throw Exception("There are no customers in line.")
        }
//        conversion of all elements of the list to their respective ticketNum only
        var queueUsersListTicketNumbers = queueUsersList.map {it.ticketNum}

//        setting of currentCustomer being entertained by counter
        var currentCustomer = queueUsersList[0]

        counter.currentCustomer = currentCustomer

        println(queueUsersList)
        println(queueUsersListTicketNumbers)
        println("Current Customer: " + currentCustomer.ticketNum)

    }
}

