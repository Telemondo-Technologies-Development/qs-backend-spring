package com.telemondo.qs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.telemondo.qs.entity.CounterType
import java.time.Instant


data class CounterDTO(
    val id: String,
    val name: String,
//   -3 = deleted, -1 = inactive, 1 = receiving, 2 = entertaining, 3 = pause
    val status: Int = -1,
    var currentCustomer: QueueUserDTO?,
    val counterType: CounterTypeDTO,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class CounterCreateDTO(
    val status: Int = -1,
    val counterTypeId: String
)

data class CounterUpdateDTO(
    val id: String,
    val name: String?,
    val status: Int?,
    val counterTypeId: String?
)

data class CounterForQueueUserDTO(
    val id: String,
    val name: String,
    val counterType: CounterTypeDTO
)

/*data class CounterUpdateStatusDTO(
    val id: String,
    val status: Int
)*/


