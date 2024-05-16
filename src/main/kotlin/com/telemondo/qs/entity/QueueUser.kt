package com.telemondo.qs.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
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
//    1 = regular, 2 = non-regular
//    initialize an initial value to satisfy kotline req to be initialize
//    regular until proven non-regular
    open var customerType: Int = 1
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
