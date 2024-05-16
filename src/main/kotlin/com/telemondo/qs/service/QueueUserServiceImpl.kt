package com.telemondo.qs.service

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.repository.QueueUserRepository
import com.telemondo.qs.utils.mapper.QueueUserMapper
import jakarta.transaction.Transactional
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Service
class QueueUserServiceImpl(
    private val queueUserRepository: QueueUserRepository,
    private val queueUserMapper: QueueUserMapper = Mappers.getMapper(QueueUserMapper::class.java),
    private val counterTypeRepository: CounterTypeRepository
) : QueueUserService {


    @Transactional
    override fun getQueueUsers(): List<QueueUserDTO> {
        val queueUsers = queueUserRepository.findAll()

        if(queueUsers.count() == 0){
            throw Exception("There are no queue users yet.")
        }

        return queueUsers.map{
            queueUserMapper.toDomain(it)
        }

    }

    @Transactional
    override fun getQueueUser(id: String): QueueUserDTO{
        val queueUser = queueUserRepository.findById(id)

        if(queueUser.isEmpty){
            throw Exception("Queue user with id $id does not exist.")
        }

        return queueUserMapper.toDomain(queueUser.get())

    }

    @Transactional
    override fun createQueueUser(queueUserCreateDTO: QueueUserCreateDTO): QueueUserDTO {

        val queueUser = queueUserMapper.mapCreateToEntity(queueUserCreateDTO)

        val counterType = counterTypeRepository.findById(queueUserCreateDTO.counterTypeId).orElseThrow{Exception("Counter type with id ${queueUserCreateDTO.counterTypeId} does not exist.")}

        val ticketNum = generateTicketNum(queueUserCreateDTO)

        queueUser.counterType = counterType

        queueUser.ticketNum = ticketNum

        queueUserRepository.save(queueUser)

        val queueUserDomain = queueUserMapper.toDomain(queueUser)

        return queueUserDomain
    }

    @Transactional
    override fun updateQueueUser(queueUserDTO: QueueUserDTO): QueueUserDTO {

        val queueUser = queueUserRepository.findById(queueUserDTO.id)

        if(queueUser.isEmpty) {
            throw Exception("Counter with ID ${queueUserDTO.id} does not exist.")
        }

        val queueUserEntity = queueUserMapper.mapUpdateToEntity(queueUserDTO, queueUser.get())

        queueUserRepository.save(queueUserEntity)

        return queueUserMapper.toDomain(queueUserEntity)
    }

    @Transactional
    override fun updateQueueUserStatus(queueUserUpdateStatusDTO: QueueUserUpdateStatusDTO): QueueUserDTO {

        val queueUser = queueUserRepository.findById(queueUserUpdateStatusDTO.id)

        if(queueUser.isEmpty){
            throw Exception("Counter with ID ${queueUserUpdateStatusDTO.id} does not exist.")
        }

        val queueUserEntity = queueUserMapper.mapUpdateStatusToEntity(queueUserUpdateStatusDTO, queueUser.get())

        queueUserRepository.save(queueUserEntity)

        return queueUserMapper.toDomain(queueUserEntity)
    }

    @Transactional
    override fun deleteQueueUser(id: String) {

        val queueUser = queueUserRepository.existsById(id)

        if(!queueUser){
            throw Exception("Counter with ID $id does not exist.")
        }

        queueUserRepository.deleteById(id)
    }



    @Transactional
    override fun generateTicketNum(queueUserCreateDTO: QueueUserCreateDTO): String {

        var suffix = ""
//        prefix for the ticketNumber according to counterType
        val prefix = counterTypeRepository.findById(queueUserCreateDTO.counterTypeId).get().prefix.toString()

//        how to make suffix dynamic to other customerTypes that potential client might have
//        this if statement is just hardcoding suffixes that is tailor-fit for our use-case
        val customerType = queueUserCreateDTO.customerType

        if(customerType != 1) {
            suffix = "S"
        }

        val time12AM = "00:00:00"
        val currentDate = LocalDate.now().toString()
        val currentDateTime12AM = currentDate + "T" + time12AM
        val ldt = LocalDateTime.parse(currentDateTime12AM)
        val instant = ldt.atZone(ZoneId.systemDefault()).toInstant()

//        SQL query to return the count of all records after current instant date with dynamic countertype and customertype filter
        var count = queueUserRepository.countByCounterTypeIdAndCustomerTypeAndCreatedAtAfter(queueUserCreateDTO.counterTypeId, customerType, instant).toInt()

        count += 1
        
        return prefix + "-" + count.toString().padStart(3, '0') + suffix
    }
}