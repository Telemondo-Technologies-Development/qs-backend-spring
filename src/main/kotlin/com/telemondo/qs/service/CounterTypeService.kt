package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import org.springframework.stereotype.Service

@Service
interface CounterTypeService {

    fun getCounterTypes(): List<CounterTypeDTO>

    fun getCounterType(id: String): CounterTypeDTO

    fun createCounterType(counterTypeCreateDTO: CounterTypeCreateDTO): CounterTypeDTO

    fun updateCounterType(counterTypeDTO: CounterTypeDTO): CounterTypeDTO

    fun delCounterType(id: String)

//    @Autowired
//    lateinit var counterTypeRepository: CounterTypeRepository
//
//    fun saveCounterType(counterType : CounterType): CounterType {
//        return counterTypeRepository.save(counterType)
//    }
//
//    fun updateCounterType(counterType : CounterType, counterTypeId: String): CounterType {
//        val newCounterType : CounterType = counterTypeRepository.getReferenceById(counterTypeId);
//        newCounterType.counterName = counterType.counterName;
//        newCounterType.createdAt = counterType.createdAt;
//        newCounterType.updatedAt = counterType.updatedAt;
//
//        return counterTypeRepository.save(newCounterType);
//
//    }
//
//    fun retrieveCounterType() : List<CounterType>?{
//        return counterTypeRepository.findAll();
//    }
//
//    fun deleteCounterType(counterTypeId : String){
//        counterTypeRepository.deleteById(counterTypeId);
//
//    }
}
