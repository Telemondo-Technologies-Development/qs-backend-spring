package com.telemondo.qs.dto

import java.time.Instant

data class CounterTypeDTO(
    val id: String,
//  -3 = deleted, 1 = active
    val status: Int = 1,
    val counterName: String,
    val prefix: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class CounterTypeCreateDTO(
    val counterName: String,
    val prefix: String
)

data class CounterTypeUpdateDTO(
    val id: String,
    val status: Int?,
    val counterName: String?,
    val prefix: String?
)
