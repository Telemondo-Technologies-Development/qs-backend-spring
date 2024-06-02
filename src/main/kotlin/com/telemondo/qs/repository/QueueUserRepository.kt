package com.telemondo.qs.repository

import com.telemondo.qs.dto.CounterForQueueUserDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.entity.QueueUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant


@Repository
interface QueueUserRepository: JpaRepository<QueueUser, String>, QueueUserDynamicFilterRepository {

    fun countByCounterTypeIdAndCustomerTypeAndCreatedAtAfter(counterTypeId: String, customerType: Int, createdAt: Instant): String

    fun findByCounterTypeIdAndCustomerTypeAndStatusAndCreatedAtAfterOrderByTicketNumAsc(counterTypeId: String,
                                                                                        customerType: Int,
                                                                                        status: Int,
                                                                                        createdAt: Instant): List<QueueUser>

    fun findTop10ByCounterTypeIdOrderByEntertainedAtDesc(counterTypeId: String): List<QueueUser>
}