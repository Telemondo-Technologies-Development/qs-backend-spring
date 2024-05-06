package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.entity.QueueUser
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.springframework.stereotype.Service

@Service
@Mapper
interface QueueUserMapper {

    fun toDomain(entity:QueueUser): QueueUserDTO

    fun toEntity(domain: QueueUserDTO): QueueUser

    fun mapCreateToEntity(req: QueueUserCreateDTO): QueueUser

    fun mapUpdateToEntity (req: QueueUserDTO, @MappingTarget queueUser: QueueUser): QueueUser

    fun mapUpdateStatusToEntity (req: QueueUserUpdateStatusDTO, @MappingTarget queueUser: QueueUser): QueueUser
}