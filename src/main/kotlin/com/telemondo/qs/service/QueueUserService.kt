package com.telemondo.qs.service

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.web.controller.QueueUserController.QueueUserFilter
import org.springframework.stereotype.Service

@Service
interface QueueUserService {

    fun getQueueUsers(queueUserFilter: QueueUserFilter): List<QueueUserDTO>

    fun getQueueUser(id: String): QueueUserDTO

    fun createQueueUser(queueUserCreateDTO: QueueUserCreateDTO): QueueUserDTO

    fun updateQueueUser(queueUserUpdateDTO: QueueUserUpdateDTO): QueueUserDTO

    fun updateQueueUserStatus(queueUserUpdateStatusDTO: QueueUserUpdateStatusDTO): QueueUserDTO

    fun deleteQueueUser(id: String)

    fun generateTicketNum(queueUserCreateDTO: QueueUserCreateDTO): String

    fun calculateEstimatedWaitTime(counterTypeId: String): String

}