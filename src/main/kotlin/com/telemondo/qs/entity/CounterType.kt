package com.telemondo.qs.entity

import jakarta.persistence.*
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.Date

@Entity
open class CounterType{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open lateinit var id: String
    open lateinit var counterName: String

    @CreationTimestamp
    open lateinit var createdAt: Instant

    @UpdateTimestamp
    open lateinit var updatedAt: Instant
}

/*
Is the data should like this in the database?
Unique ID | Counter Name | Created At | Updated At
 */