package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.dto.CounterTypeUpdateDTO
import com.telemondo.qs.web.controller.CounterTypeControllers.CounterTypeFilter
import org.springframework.stereotype.Service

@Service
interface CounterTypeService {

    fun getCounterTypes(counterTypeFilter: CounterTypeFilter): List<CounterTypeDTO>

    fun getCounterType(id: String): CounterTypeDTO

    fun createCounterType(counterTypeCreateDTO: CounterTypeCreateDTO): CounterTypeDTO

    fun updateCounterType(counterTypeUpdateDTO: CounterTypeUpdateDTO): CounterTypeDTO

    fun delCounterType(id: String)

}