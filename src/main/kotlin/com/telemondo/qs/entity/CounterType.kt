package com.telemondo.qs.entity

import jakarta.persistence.*
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.Date

@Entity
@Table(name = "counter_type")
data class CounterType(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "counterName", nullable = false)
    var counterName: String,

    @CreationTimestamp
    var createdAt: Date? = null,

    @UpdateTimestamp
    var updatedAt: Date? = null
)

/*
Is the data should like this in the database?
Unique ID | Counter Name | Created At | Updated At
 */