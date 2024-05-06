package com.telemondo.qs.dto

import java.time.Instant

data class CounterTypeDTO(
    val id: String,
    val counterName: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class CounterTypeCreateDTO(
    val counterName: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
