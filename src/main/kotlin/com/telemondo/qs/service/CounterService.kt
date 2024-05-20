package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateStatusDTO
import org.springframework.stereotype.Service

@Service
interface CounterService {

    fun getCounters(startingPage: Int, pageSize: Int): List<CounterDTO>

    fun getCounter(id: String): CounterDTO

    fun createCounter(counterCreateDTO: CounterCreateDTO): CounterDTO

    fun updateCounter(counterDTO: CounterDTO): CounterDTO

    fun delCounter(id: String)

//    fun updateStatus(counterUpdateStatusDTO: CounterUpdateStatusDTO): CounterDTO

    fun counterDoNextCustomer(id: String, customerType: Int)

    fun pauseCounter(id: String)

    fun turnOffCounter(id: String)
}