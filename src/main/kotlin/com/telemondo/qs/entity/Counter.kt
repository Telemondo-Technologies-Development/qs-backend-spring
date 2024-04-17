package com.telemondo.qs.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.Instant

@Entity
data class Counter (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    val name: String,
    val status: Int = -1,
    @ManyToOne
    @JoinColumn(name = "counter_type_id")
    val counterType: CounterType,
    val createdAt: Instant,
    val updatedAt: Instant
)