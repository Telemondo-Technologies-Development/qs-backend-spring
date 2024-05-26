package com.telemondo.qs.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
open class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open lateinit var id: String
    @OneToOne
    @JoinColumn(name = "queue_user_id")
    open var currentCustomer: QueueUser? = null
    open lateinit var name: String
    open var status: Int = -1
    @ManyToOne
    @JoinColumn(name = "counter_type_id")
    open lateinit var counterType: CounterType
    @CreationTimestamp
    open var createdAt: Instant = Instant.now()
    @UpdateTimestamp
    open var updatedAt: Instant = Instant.now()
}