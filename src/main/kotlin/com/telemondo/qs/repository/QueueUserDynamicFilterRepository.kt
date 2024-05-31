package com.telemondo.qs.repository

import com.telemondo.qs.entity.QueueUser
import com.telemondo.qs.web.controller.QueueUserController.QueueUserFilter

interface QueueUserDynamicFilterRepository {
    fun findDynamicQueueUsers(filter: QueueUserFilter): List<QueueUser>
}