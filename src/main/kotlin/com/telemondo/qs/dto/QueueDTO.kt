package com.telemondo.qs.dto


import java.time.Instant

data class QueueDTO(
    val id: String,
    val counterType: CounterTypeDTO,
    val counter: CounterDTO,
    val status: Int = 1,
    val createdAt: Instant,
    val updatedAt: Instant
)
