package com.telemondo.qs.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.Instant

@Entity
open class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open lateinit var id: String
    open lateinit var name: String
    open var status: Int = -1

    @ManyToOne
    @JoinColumn(name = "counter_type_id")
    open var counterType: CounterType? = null
    open lateinit var createdAt: Instant
    open lateinit var updatedAt: Instant
}