package com.telemondo.qs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.telemondo.qs.entity.CounterType
import java.time.Instant


data class CounterDTO(
    val id: String,
    val name: String,
//    -1 = inactive, 1 = receiving, 2 = entertaining, 3 = pause
    val status: Int = -1,
    val counterType: CounterTypeDTO,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class CounterCreateDTO(
    val name: String,
    val status: Int = -1,
    val counterTypeId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class CounterUpdateStatusDTO(
    val id: String,
    val status: Int,
    val updatedAt: Instant
)