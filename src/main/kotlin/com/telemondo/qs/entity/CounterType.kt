package com.telemondo.qs.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

data class CounterType(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    val counterType: String,
    val createdAt: Instant,
    val lastUpdated: Instant
)
