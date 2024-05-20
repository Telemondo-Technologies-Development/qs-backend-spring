package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.repository.CounterTypeRepository
import com.telemondo.qs.utils.mapper.CounterTypeMapper
import jakarta.transaction.Transactional
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable

@Service
class CounterTypeServiceImpl(
    private val counterTypeRepository: CounterTypeRepository,
    private val counterTypeMapper: CounterTypeMapper = Mappers.getMapper(CounterTypeMapper::class.java)
) : CounterTypeService {

    @Transactional
    override fun getCounterTypes(startingPage: Int, pageSize: Int): List<CounterTypeDTO> {
        val pageable: Pageable = PageRequest.of(startingPage, pageSize, Sort.by(Sort.Direction.ASC, "counterName"))
        val countertypes = counterTypeRepository.findAll(pageable)
        if(countertypes.count() == 0){
            throw Exception("No counter types yet.")
        }

        return countertypes.content.map{
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
    override fun updateCounterType(counterTypeDTO: CounterTypeDTO): CounterTypeDTO {

        val counterType = counterTypeRepository.findById(counterTypeDTO.id).orElseThrow{Exception("Counter type with id ${counterTypeDTO.id} does not exist." )}
        val counterTypeEntity = counterTypeMapper.mapUpdateToEntity(counterTypeDTO, counterType)
        counterTypeRepository.save(counterTypeEntity)
        return counterTypeMapper.toDomain(counterTypeEntity)

    }

    @Transactional
    override fun delCounterType(id: String) {
        counterTypeRepository.deleteById(id)
    }
}