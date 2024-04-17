package com.telemondo.qs.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.Instant

@Entity
data class Queue(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    @ManyToOne
    @JoinColumn(name = "counter_type_id")
    val counterType: CounterType,
    @ManyToOne
    @JoinColumn(name = "counter_id")
    val counter: Counter,
    val status: Int = 1,
    val createdAt: Instant,
    val updatedAt: Instant
//    removed this because this can be a transient property, no need to permanently store to database
//    val currentSize: Int
)
