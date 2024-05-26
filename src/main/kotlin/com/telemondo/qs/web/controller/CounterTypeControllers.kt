package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.CounterTypeCreateDTO
import com.telemondo.qs.dto.CounterTypeDTO
import com.telemondo.qs.dto.CounterTypeUpdateDTO
import com.telemondo.qs.service.CounterTypeService
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
import java.time.Instant


@RestController
@RequestMapping("counterType")
class CounterTypeControllers(
    private val counterTypeService: CounterTypeService
) {

//    data class PageableObject (
//        val startingPage: Int,
//        val pageSize: Int
//    )

    data class CounterTypeFilter(
//        currentPage = -1 to go the last page
        var currentPage: Int,
//        pageSize = -1 to retrieve ALL records
        var pageSize: Int,
        val id: String?,
        val counterName: String?,
        val createdAt: Instant?,
        val updatedAt: Instant?,
        val prefix: String?,
        val sortField: String?,
        val sortDirection: String?
    )

    @GetMapping
    fun getCounterTypes(@RequestBody counterTypeFilter: CounterTypeFilter): ResponseEntity<List<CounterTypeDTO>> {
        return ResponseEntity.ok(counterTypeService.getCounterTypes(counterTypeFilter))
    }

    @GetMapping("/{id}")
    fun getCounterType(@PathVariable id: String): ResponseEntity<CounterTypeDTO>{
        return ResponseEntity.ok(counterTypeService.getCounterType(id))
    }

    @PostMapping
    fun createCounterType(@RequestBody counterTypeCreateDTO: CounterTypeCreateDTO): ResponseEntity<CounterTypeDTO> {
        return ResponseEntity.ok(counterTypeService.createCounterType(counterTypeCreateDTO))
    }

    @PutMapping
    fun updateCounterType(@RequestBody counterTypeUpdateDTO: CounterTypeUpdateDTO): ResponseEntity<CounterTypeDTO> {
        return ResponseEntity.ok(counterTypeService.updateCounterType(counterTypeUpdateDTO))
    }

    @DeleteMapping("/{id}")
    fun delCounterType(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity(counterTypeService.delCounterType(id), HttpStatus.NO_CONTENT)
    }
}
