package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateDTO
import com.telemondo.qs.web.controller.CounterController.CounterFilter
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface CounterService {

    fun getCounters(counterFilter: CounterFilter): List<CounterDTO>

    fun getCounter(id: String): CounterDTO

    fun createCounter(counterCreateDTO: CounterCreateDTO): CounterDTO

    fun updateCounter(counterUpdateDTO: CounterUpdateDTO): CounterDTO

    fun delCounter(id: String)

    fun counterNextCustomer(id: String, customerType: Int)

    fun noShowCounterCustomer(id: String)

    fun finishCounterCustomer(id: String)

    fun nextRegularCustomer(id:String)

    fun nextNonRegularCustomer(id:String)

    fun getCounterScreens(): Map<String, String>

    fun pauseCounter(id: String)

    fun turnOffCounter(id: String)

}