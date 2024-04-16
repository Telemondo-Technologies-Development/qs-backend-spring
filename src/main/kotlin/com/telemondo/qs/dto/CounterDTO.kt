package com.telemondo.qs.dto

import com.telemondo.qs.entity.CounterType
import java.time.Instant

data class CounterDTO(
    val id: String,
    val name: String,
    val status: Int = 3,
    val counterType: CounterTypeDTO,
    val createdAt: Instant,
    val lastUpdated: Instant
)
