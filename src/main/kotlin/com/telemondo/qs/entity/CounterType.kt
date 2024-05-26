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
    @Column(unique = true)
    open lateinit var prefix : String
    @CreationTimestamp
    open var createdAt: Instant = Instant.now()
    @UpdateTimestamp
    open var updatedAt: Instant = Instant.now()
}
