package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.entity.Counter
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.springframework.stereotype.Service

@Service
@Mapper
interface CounterMapper {

    fun toDomain(entity: Counter): CounterDTO

    fun toEntity(domain: CounterDTO): Counter

    fun mapCreateToEntity(req: CounterCreateDTO): Counter

    fun mapUpdateToEntity(req: CounterDTO, @MappingTarget counter: Counter): Counter
}