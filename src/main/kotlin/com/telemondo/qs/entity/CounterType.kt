package com.telemondo.qs.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class CounterType(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    val counterType: String
)
