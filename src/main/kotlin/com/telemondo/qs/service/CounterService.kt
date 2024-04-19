package com.telemondo.qs.service

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import org.springframework.stereotype.Service

@Service
interface CounterService {

    fun getCounters(): List<CounterDTO>

    fun createCounter(counterCreateDTO: CounterCreateDTO): CounterDTO

    fun updateCounter(counterDTO: CounterDTO): CounterDTO

    fun delCounter(id: String)

    fun changeStatus(status: String): CounterDTO
}