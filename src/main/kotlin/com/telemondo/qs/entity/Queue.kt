package com.telemondo.qs.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Queue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val queueType: String,
    val status: String = "ACTIVE"
//    removed this because this can be a transient property, no need to permanently store to database
//    val currentSize: Int
)
