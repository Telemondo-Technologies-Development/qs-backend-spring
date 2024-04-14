//package com.telemondo.qs.entity
//
//import jakarta.persistence.Entity
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GenerationType
//import jakarta.persistence.Id
//
//
//@Entity
//data class Customer(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
////    removed this because this can be a transient property, no need to permanently store to database
////    val currentPos: Int,
////    val queueNumber: Int,
//    val queueTypeId: Int,
//)
//
