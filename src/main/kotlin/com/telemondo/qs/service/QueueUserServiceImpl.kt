package com.telemondo.qs.service

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.repository.QueueUserRepository
import com.telemondo.qs.utils.mapper.QueueUserMapper
import com.telemondo.qs.web.controller.QueueUserController.QueueUserFilter
import io.nats.client.Connection
import jakarta.transaction.Transactional
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


@Service
class QueueUserServiceImpl(
    private val queueUserRepository: QueueUserRepository,
    private val queueUserMapper: QueueUserMapper = Mappers.getMapper(QueueUserMapper::class.java),
    private val counterTypeRepository: CounterTypeRepository,
    private val natsConnection: Connection
) : QueueUserService {


    @Transactional
//    pageSize = -1 is to get ALL the results
    override fun getQueueUsers(queueUserFilter: QueueUserFilter): List<QueueUserDTO> {
        val queueUsers = queueUserRepository.findDynamicQueueUsers(queueUserFilter)
        if (queueUsers.isEmpty()){
            throw Exception("No Queue Users.")
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
//        sends a notification to frontend that queueUser was created
        val message = "Queue user created: ${queueUser.ticketNum}"
        natsConnection.publish("queue-user-created", message.toByteArray())
////        for frontend
//        // Example using NATS.js library for Node.js
//        const { connect } = require('nats');
//
//        connect({ servers: 'nats://localhost:4222' }).then((nc) => {
//            const opts = nc.subscriptionOptions();
//            opts.setManualAckMode(true);
//
//            const sub = nc.subscribe('queue-user-created', opts);
//            sub.on('message', (msg) => {
//                console.log('Received message:', msg.getData());
//                // Process the received message, e.g., update UI
//            });
//        });
        val queueUserDomain = queueUserMapper.toDomain(queueUser)
        return queueUserDomain
    }

    @Transactional
    override fun updateQueueUser(queueUserUpdateDTO: QueueUserUpdateDTO): QueueUserDTO {

        val queueUser = queueUserRepository.findById(queueUserUpdateDTO.id)

        if(queueUser.isEmpty) {
            throw Exception("Counter with ID ${queueUserUpdateDTO.id} does not exist.")
        }

        val queueUserEntity = queueUserMapper.mapUpdateToEntity(queueUserUpdateDTO, queueUser.get())

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