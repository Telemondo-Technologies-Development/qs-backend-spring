package com.telemondo.qs.service

import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.repository.CounterTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CounterTypeService {

    @Autowired
    lateinit var counterTypeRepository: CounterTypeRepository

    fun saveCounterType(counterType : CounterType): CounterType {
        return counterTypeRepository.save(counterType)
    }

    fun updateCounterType(counterType : CounterType, counterTypeId: String): CounterType {
        val newCounterType : CounterType = counterTypeRepository.getReferenceById(counterTypeId);
        newCounterType.counterName = counterType.counterName;
        newCounterType.createdAt = counterType.createdAt;
        newCounterType.updatedAt = counterType.updatedAt;

        return counterTypeRepository.save(newCounterType);

    }

    fun retrieveCounterType() : List<CounterType>?{
        return counterTypeRepository.findAll();
    }

    fun deleteCounterType(counterTypeId : String){
        counterTypeRepository.deleteById(counterTypeId);

    }
}
