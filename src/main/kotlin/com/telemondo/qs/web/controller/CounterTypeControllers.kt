package com.telemondo.qs.web.controller

import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.service.CounterTypeService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/api")
class CounterTypeControllers {

    @Autowired
    lateinit var counterTypeService: CounterTypeService

    @PostMapping("/CounterType")
    fun saveCounterType(@Valid @RequestBody counterType: CounterType): CounterType {
        return counterTypeService.saveCounterType(counterType)
    }

    @GetMapping("/CounterType")
    fun retrieveCounterType(): List<CounterType>? {
        return counterTypeService.retrieveCounterType()
    }

    @PutMapping("/CounterType/{id}")
    fun updateCounterType(
        @PathVariable("id") counterTypeId: String,
        @Valid @RequestBody counterType: CounterType
    ): CounterType {
        return counterTypeService.updateCounterType(counterType, counterTypeId)
    }

    @DeleteMapping("/CounterType/{id}")
    fun deleteCounterType(
        @PathVariable("id") counterTypeId: String
    ) {
        counterTypeService.deleteCounterType(counterTypeId)
    }
}
