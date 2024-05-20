package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.CounterCreateDTO
import com.telemondo.qs.dto.CounterDTO
import com.telemondo.qs.dto.CounterUpdateStatusDTO
import com.telemondo.qs.entity.QueueUser
import com.telemondo.qs.service.CounterService
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
@RequestMapping("/counter")
class CounterController(
    private val counterService: CounterService
) {

    data class pageableObject (
        val startingPage: Int,
        val pageSize: Int
    )

    @GetMapping
    fun getCounters(@RequestBody pageableObject: pageableObject): ResponseEntity<List<CounterDTO>>{
        return ResponseEntity.ok(counterService.getCounters(pageableObject.startingPage, pageableObject.pageSize))
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
    fun updateCounter(@RequestBody counterDTO: CounterDTO): ResponseEntity<CounterDTO>{
        return ResponseEntity.ok(counterService.updateCounter(counterDTO))
    }

//    @PutMapping("/changeStatus")
//    fun updateCounterStatus(@RequestBody counterUpdateStatusDTO: CounterUpdateStatusDTO): ResponseEntity<CounterDTO>{
//        return ResponseEntity.ok(counterService.updateStatus((counterUpdateStatusDTO)))
//    }

    data class nextCustomerRequest(
        val id: String,
        val customerType: Int
            )

    @PutMapping("/nextCustomer")
    fun nextCustomer(@RequestBody nextCustomerRequest: nextCustomerRequest): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.counterDoNextCustomer(nextCustomerRequest.id, nextCustomerRequest.customerType))
    }

    @PutMapping("/pauseCounter/{id}")
    fun pauseCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.pauseCounter(id))
    }

    @PutMapping("/turnOffCounter/{id}")
    fun turnOffCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity.ok(counterService.turnOffCounter(id))
    }

    @DeleteMapping("/{id}")
    fun deleteCounter(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity(counterService.delCounter(id), HttpStatus.NO_CONTENT)
    }
}