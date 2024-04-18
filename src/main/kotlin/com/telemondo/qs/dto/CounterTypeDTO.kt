package com.telemondo.qs.dto

import java.util.Date

data class CounterTypeDTO(
    val id: String,
    val counterType: String,
    val createdAt: Date,
    val updatedAt: Date
)
