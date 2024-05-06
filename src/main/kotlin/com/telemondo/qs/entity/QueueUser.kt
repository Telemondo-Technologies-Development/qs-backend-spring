package com.telemondo.qs.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant


@Entity
open class QueueUser{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open lateinit var id: String
    @Column
    open lateinit var ticketNum: String

    @ManyToOne
    @JoinColumn(name = "counter_type_id")
    open var counterType: CounterType? = null
    @ManyToOne
    @JoinColumn(name = "counter_id")
    open var counter: Counter? = null
    @Column
    open var status: Int = 1

    @CreationTimestamp
    open lateinit var createdAt: Instant

    @UpdateTimestamp
    open lateinit var updatedAt: Instant
}
