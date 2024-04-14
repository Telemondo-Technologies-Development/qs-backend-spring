package com.telemondo.qs.dto

data class MovieDTO(
//    default values dont work
    var id: Int,
    var name: String = "Default Movie",
    var rating: Double
)
