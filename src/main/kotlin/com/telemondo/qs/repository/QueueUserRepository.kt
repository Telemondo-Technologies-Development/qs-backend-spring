package com.telemondo.qs.repository

import com.telemondo.qs.entity.QueueUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime

@Repository
interface QueueUserRepository: CrudRepository<QueueUser, String> {

    fun countByCreatedAtAfter(dateTime: Instant): String

    fun findByStatusAndCreatedAtAfterOrderByTicketNumAsc(status: Int, createdAt: Instant): List<QueueUser>

}