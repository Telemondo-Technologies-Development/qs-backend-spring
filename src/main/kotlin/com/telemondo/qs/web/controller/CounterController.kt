package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateDTO
import com.telemondo.qs.service.CounterService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/counter")
class CounterController(
    private val counterService: CounterService
) {

    data class CounterFilter (
//        currentPage = -1 to go the last page
        var currentPage: Int = 0,
//        pageSize = -1 to retrieve ALL records
        var pageSize: Int = 10,
        val id: String?,
        val status: Int?,
        val name: String?,
        val currentCustomerId: String?,
        val counterTypeId: String?,
        val createdAt: Instant?,
        val updatedAt: Instant?,
        val sortField: String?,
        val sortDirection: String?
    )

    @GetMapping
    fun getCounters(@RequestParam(required = false) currentPage: Int?,
                    @RequestParam(required = false) pageSize: Int?,
                    @RequestParam(required = false) id: String?,
                    @RequestParam(required = false) status: Int?,
                    @RequestParam(required = false) name: String?,
                    @RequestParam(required = false) currentCustomerId: String?,
                    @RequestParam(required = false) counterTypeId: String?,
                    @RequestParam(required = false) createdAt: Instant?,
                    @RequestParam(required = false) updatedAt: Instant?,
                    @RequestParam(required = false) sortField: String?,
                    @RequestParam(required = false) sortDirection: String?): ResponseEntity<List<CounterDTO>>{
        val filter = CounterFilter(
            currentPage = currentPage ?: 0,
            pageSize = pageSize ?: 10, // Change default value to -1 to retrieve all records
            id = id,
            status = status,
            name = name,
            currentCustomerId = currentCustomerId,
            counterTypeId = counterTypeId,
            createdAt = createdAt,
            updatedAt = updatedAt,
            sortField = sortField,
            sortDirection = sortDirection
        )
        return ResponseEntity.ok(counterService.getCounters(filter))
    }

    @GetMapping("/{id}")
    fun getCounter(@PathVariable id:String): ResponseEntity<CounterDTO>{
        return ResponseEntity.ok(counterService.getCounter(id))
    }

    @PostMapping
    fun createCounter(@RequestBody counterCreateDTO: CounterCreateDTO): ResponseEntity<CounterDTO>{
        return ResponseEntity.ok(counterService.createCounter(counterCreateDTO))
    }

    @PutMapping
    fun updateCounter(@RequestBody counterUpdateDTO: CounterUpdateDTO): ResponseEntity<CounterDTO>{
        return ResponseEntity.ok(counterService.updateCounter(counterUpdateDTO))
    }

//    @PutMapping("/changeStatus")
//    fun updateCounterStatus(@RequestBody counterUpdateStatusDTO: CounterUpdateStatusDTO): ResponseEntity<CounterDTO>{
//        return ResponseEntity.ok(counterService.updateStatus((counterUpdateStatusDTO)))
//    }

    @PutMapping("/nextRegularCustomer/{id}")
    fun nextRegularCustomer(@PathVariable id:String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.nextRegularCustomer(id))
    }

    @PutMapping("/nextNonRegularCustomer/{id}")
    fun nextNonRegularCustomer(@PathVariable id:String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.nextNonRegularCustomer(id))
    }

    @PutMapping("/noShowCustomer/{id}")
    fun noShowCounterCustomer(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.noShowCounterCustomer(id))
    }

    @PutMapping("/finishCustomer/{id}")
    fun finishCounterCustomer(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.finishCounterCustomer(id))
    }

    @PutMapping("/pauseCounter/{id}")
    fun pauseCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.pauseCounter(id))
    }

    @PutMapping("/turnOffCounter/{id}")
    fun turnOffCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.turnOffCounter(id))
    }

    @PutMapping("/delete/{id}")
    fun deleteCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity(counterService.delCounter(id), HttpStatus.NO_CONTENT)
    }
}