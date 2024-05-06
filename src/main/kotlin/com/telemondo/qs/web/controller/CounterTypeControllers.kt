package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.entity.Counter
import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.service.CounterTypeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("counterType")
class CounterTypeControllers(
    private val counterTypeService: CounterTypeService
) {
    @GetMapping
    fun getCounterTypes(): ResponseEntity<List<CounterTypeDTO>> {
        return ResponseEntity.ok(counterTypeService.getCounterTypes())
    }

    @PostMapping
    fun createCounterType(@RequestBody counterTypeCreateDTO: CounterTypeCreateDTO): ResponseEntity<CounterTypeDTO> {
        return ResponseEntity.ok(counterTypeService.createCounterType(counterTypeCreateDTO))
    }

    @PutMapping
    fun updateCounterType(@RequestBody counterTypeDTO: CounterTypeDTO): ResponseEntity<CounterTypeDTO> {
        return ResponseEntity.ok(counterTypeService.updateCounterType(counterTypeDTO))
    }

    @DeleteMapping
    fun delCounterType(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity(counterTypeService.delCounterType(id), HttpStatus.NO_CONTENT)
    }
}
