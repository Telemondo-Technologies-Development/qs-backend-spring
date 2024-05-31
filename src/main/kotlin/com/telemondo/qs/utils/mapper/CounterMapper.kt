package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateDTO
import com.telemondo.qs.entity.Counter
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.springframework.stereotype.Service

@Service
@Mapper
interface CounterMapper {

    fun toDomain(entity: Counter): CounterDTO

    fun toEntity(domain: CounterDTO): Counter

    fun mapCreateToEntity(req: CounterCreateDTO): Counter

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun mapUpdateToEntity(req: CounterUpdateDTO, @MappingTarget counter: Counter): Counter

    fun mapUpdateStatusToEntity(req: CounterUpdateDTO, @MappingTarget counter: Counter): Counter
}