package com.telemondo.qs.utils.mapper
//this interface is for the conversion of entity to DTO and vice versa

//D=DTO, E=Entity
interface Mapper <D, E> {

    fun fromEntity(entity: E): D
    fun toEntity(domain: D): E
}