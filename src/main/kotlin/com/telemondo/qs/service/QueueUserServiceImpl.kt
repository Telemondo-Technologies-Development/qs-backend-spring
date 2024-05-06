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

        val ticketNum = generateTicketNum()

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
    override fun generateTicketNum(): String {
        val time12AM = "00:00:00"
        val currentDate = LocalDate.now().toString()
        val currentDateTime12AM = currentDate + "T" + time12AM
        val ldt = LocalDateTime.parse(currentDateTime12AM)
        val instant = ldt.atZone(ZoneId.systemDefault()).toInstant()

        var count = queueUserRepository.countByCreatedAtAfter(instant).toInt()

        count += 1
        
        return "T-" + count.toString().padStart(3, '0')
    }
}