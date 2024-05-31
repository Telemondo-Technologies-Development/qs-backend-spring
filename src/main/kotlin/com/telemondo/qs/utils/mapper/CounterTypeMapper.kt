package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.dto.CounterTypeUpdateDTO
import com.telemondo.qs.entity.CounterType
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.springframework.stereotype.Service

@Service
@Mapper
interface CounterTypeMapper {

    fun toDomain(entity: CounterType): CounterTypeDTO

    fun toEntity(domain: CounterTypeDTO): CounterType

    fun mapCreateToEntity(counterTypeCreateDTO: CounterTypeCreateDTO): CounterType

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun mapUpdateToEntity(req: CounterTypeUpdateDTO, @MappingTarget counterType: CounterType): CounterType
}