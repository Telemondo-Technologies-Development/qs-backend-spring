package com.telemondo.qs.dto

import java.time.Instant

data class CounterTypeDTO(
    val id: String,
    val counterType: String,
    val createdAt: Instant,
    val lastUpdated: Instant
)
