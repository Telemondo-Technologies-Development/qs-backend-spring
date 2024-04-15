package com.telemondo.qs.dto

import com.telemondo.qs.entity.CounterType

data class CounterDTO(
    val id: String,
    val name: String,
    val status: Int = 3,
    val counterType: CounterTypeDTO
)
