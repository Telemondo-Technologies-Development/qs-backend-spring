package com.telemondo.qs.dto

import com.telemondo.qs.entity.CounterType
import org.apache.logging.log4j.CloseableThreadContext.Instance
import java.time.Instant

data class QueueDTO(
    val id: String,
    val counterType: CounterTypeDTO,
    val status: Int = 1,
    val createdAt: Instant,
    val lastUpdated: Instant
)
