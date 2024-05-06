package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.entity.Counter
import com.telemondo.qs.entity.CounterType
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.springframework.stereotype.Service

@Service
@Mapper
interface CounterTypeMapper {

    fun toDomain(entity: CounterType): CounterTypeDTO

    fun toEntity(domain: CounterTypeDTO): CounterType

    fun mapCreateToEntity(counterTypeCreateDTO: CounterTypeCreateDTO): CounterType

    fun mapUpdateToEntity(req: CounterTypeDTO, @MappingTarget counterType: CounterType): CounterType

}