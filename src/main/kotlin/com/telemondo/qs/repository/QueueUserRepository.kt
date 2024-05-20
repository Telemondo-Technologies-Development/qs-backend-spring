package com.telemondo.qs.repository

import com.telemondo.qs.entity.QueueUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant


@Repository
interface QueueUserRepository: JpaRepository<QueueUser, String> {

    fun countByCounterTypeIdAndCustomerTypeAndCreatedAtAfter(counterTypeId: String, customerType: Int, createdAt: Instant): String

    fun findByCounterTypeIdAndCustomerTypeAndStatusAndCreatedAtAfterOrderByTicketNumAsc(counterTypeId: String, customerType: Int, status: Int, createdAt: Instant): List<QueueUser>

}