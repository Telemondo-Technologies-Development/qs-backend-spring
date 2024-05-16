package com.telemondo.qs.service

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.entity.QueueUser
import org.mapstruct.MappingTarget
import org.springframework.stereotype.Service

@Service
interface QueueUserService {

    fun getQueueUsers(): List<QueueUserDTO>

    fun getQueueUser(id: String): QueueUserDTO

    fun createQueueUser(queueUserCreateDTO: QueueUserCreateDTO): QueueUserDTO

    fun updateQueueUser(queueUserDTO: QueueUserDTO): QueueUserDTO

    fun updateQueueUserStatus(queueUserUpdateStatusDTO: QueueUserUpdateStatusDTO): QueueUserDTO

    fun deleteQueueUser(id: String)

    fun generateTicketNum(queueUserCreateDTO: QueueUserCreateDTO): String

}