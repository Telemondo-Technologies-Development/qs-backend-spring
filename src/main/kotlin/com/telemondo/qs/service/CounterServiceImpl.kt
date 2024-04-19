package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.entity.Counter
import com.telemondo.qs.repository.CounterRepository
import com.telemondo.qs.utils.mapper.CounterMapper
import jakarta.transaction.Transactional
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CounterServiceImpl(
    private val counterRepository: CounterRepository,
    private val counterMapper: CounterMapper = Mappers.getMapper(CounterMapper::class.java)
): CounterService {

    @Transactional
    override fun getCounters(): List<CounterDTO> {
        val counters = counterRepository.findAll()

        if(counters.count() == 0){
            throw Exception("No counters yet.")
        }

        return counters.map{
            counterMapper.toDomain(it)
        }
    }

//    we made counterCreateDTO WITHOUT id because counterDTO has id required.
    @Transactional
    override fun createCounter(counterCreateDTO: CounterCreateDTO): CounterDTO {

        val counter = counterMapper.mapCreateToEntity(counterCreateDTO)
        counterRepository.save(counter)
        return counterMapper.toDomain(counter)
    }

    @Transactional
    override fun updateCounter(counterDTO: CounterDTO): CounterDTO {
        val counter = counterRepository.findById(counterDTO.id)

        if(counter.isEmpty){
            throw Exception("Counter with ID ${counterDTO.id} does not exist.")
        }

        counterRepository.save(counterMapper.toEntity(counterDTO))

//        much safer update function kay mas connected siya sa Mapstruct
//        in the "req" parameter which can be "CounterCreateDTO" or any DTO with missing properties, you can still map them to an entity
//        fun mapUpdateToEntity(req: CounterDTO, @MappingTarget counter: Counter): Counter

        return counterMapper.toDomain(counter.get())
    }

    @Transactional
    override fun delCounter(id: String) {
        val exists = counterRepository.existsById(id)

        if(!exists){
            throw Exception("Counter with ID $id does not exist.")
        }
        counterRepository.deleteById(id)
    }

    @Transactional
    override fun changeStatus(status: String): CounterDTO {
        TODO("Not yet implemented")
    }
}