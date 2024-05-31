package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.dto.CounterTypeUpdateDTO
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.utils.mapper.CounterTypeMapper
import com.telemondo.qs.web.controller.CounterTypeControllers.CounterTypeFilter
import jakarta.transaction.Transactional
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class CounterTypeServiceImpl(
    private val counterTypeRepository: CounterTypeRepository,
    private val counterTypeMapper: CounterTypeMapper = Mappers.getMapper(CounterTypeMapper::class.java)
) : CounterTypeService {

    @Transactional
    override fun getCounterTypes(counterTypeFilter: CounterTypeFilter): List<CounterTypeDTO> {
        val counterTypes = counterTypeRepository.findDynamicCounterTypes(counterTypeFilter)
        if (counterTypes.count() == 0) {
            throw Exception("No counter types.")
            }
        return counterTypes.map {
            counterTypeMapper.toDomain(it)
        }
    }

    @Transactional
    override fun getCounterType(id: String): CounterTypeDTO{

        val counterType = counterTypeRepository.findById(id)

        if(counterType.isEmpty){
            throw Exception("Counter type with id $id does not exist.")
        }

        return counterTypeMapper.toDomain(counterType.get())
    }

    @Transactional
    override fun createCounterType(counterTypeCreateDTO: CounterTypeCreateDTO): CounterTypeDTO {

        val counterType = counterTypeMapper.mapCreateToEntity(counterTypeCreateDTO)

        counterTypeRepository.save(counterType)

        return counterTypeMapper.toDomain(counterType)

    }

    @Transactional
    override fun updateCounterType(counterTypeUpdateDTO: CounterTypeUpdateDTO): CounterTypeDTO {

        val counterType = counterTypeRepository.findById(counterTypeUpdateDTO.id).orElseThrow{Exception("Counter type with id ${counterTypeUpdateDTO.id} does not exist." )}
        val counterTypeEntity = counterTypeMapper.mapUpdateToEntity(counterTypeUpdateDTO, counterType)
        counterTypeRepository.save(counterTypeEntity)
        return counterTypeMapper.toDomain(counterTypeEntity)

    }

    @Transactional
    override fun delCounterType(id: String) {
        counterTypeRepository.deleteById(id)
    }
}