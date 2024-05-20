package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import org.springframework.stereotype.Service

@Service
interface CounterTypeService {

    fun getCounterTypes(startingPage: Int, pageSize: Int): List<CounterTypeDTO>

    fun getCounterType(id: String): CounterTypeDTO

    fun createCounterType(counterTypeCreateDTO: CounterTypeCreateDTO): CounterTypeDTO

    fun updateCounterType(counterTypeDTO: CounterTypeDTO): CounterTypeDTO

    fun delCounterType(id: String)

}