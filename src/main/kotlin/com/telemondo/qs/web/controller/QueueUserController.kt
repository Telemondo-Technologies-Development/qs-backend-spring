package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
import com.telemondo.qs.dto.QueueUserUpdateDTO
import com.telemondo.qs.dto.QueueUserUpdateStatusDTO
import com.telemondo.qs.service.QueueUserService
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
@RequestMapping("/queueUser")
class QueueUserController(
    private val queueUserService: QueueUserService
) {
    data class QueueUserFilter (
//        currentPage = -1 to go the last page
        var currentPage: Int = 0,
//        pageSize = -1 to retrieve ALL records
        var pageSize: Int = 10,
        val id: String?,
        val status: Int?,
        val ticketNum: String?,
        val customerType: Int?,
        val counterTypeId: String?,
        val counterId: String?,
        val entertainedAt: Instant?,
        val createdAt: Instant?,
        val updatedAt: Instant?,
        val sortField: String?,
        val sortDirection: String?
    )

    @GetMapping
    fun getQueueUsers(@RequestParam(required = false) currentPage: Int?,
                      @RequestParam(required = false) pageSize: Int?,
                      @RequestParam(required = false) id: String?,
                      @RequestParam(required = false) status: Int?,
                      @RequestParam(required = false) ticketNum: String?,
                      @RequestParam(required = false) customerType: Int?,
                      @RequestParam(required = false) counterTypeId: String?,
                      @RequestParam(required = false) counterId: String?,
                      @RequestParam(required = false) createdAt: Instant?,
                      @RequestParam(required = false) entertainedAt: Instant?,
                      @RequestParam(required = false) updatedAt: Instant?,
                      @RequestParam(required = false) sortField: String?,
                      @RequestParam(required = false) sortDirection: String?): ResponseEntity<List<QueueUserDTO>>{
        val filter =  QueueUserFilter (
//        currentPage = -1 to go the last page
            currentPage = currentPage ?: 0,
//        pageSize = -1 to retrieve ALL records
            pageSize = pageSize ?: 10,
            id = id,
            status = status,
            ticketNum = ticketNum,
            customerType = customerType,
            counterTypeId = counterTypeId,
            counterId = counterId,
            createdAt = createdAt,
            entertainedAt = entertainedAt,
            updatedAt = updatedAt,
            sortField = sortField,
            sortDirection = sortDirection
        )
        return ResponseEntity.ok(queueUserService.getQueueUsers(filter))
    }

    @GetMapping("/{id}")
    fun getQueueUser(@PathVariable id: String): ResponseEntity<QueueUserDTO>{
        return ResponseEntity.ok(queueUserService.getQueueUser(id))
    }

    @PostMapping
    fun createQueueUser(@RequestBody queueUserCreateDTO: QueueUserCreateDTO): ResponseEntity<QueueUserDTO>{
        println("controller" + queueUserCreateDTO.status)
        return ResponseEntity.ok(queueUserService.createQueueUser(queueUserCreateDTO))
    }

    @PutMapping
    fun updateQueueUser(@RequestBody queueUserUpdateDTO: QueueUserUpdateDTO): ResponseEntity<QueueUserDTO>{
        return ResponseEntity.ok(queueUserService.updateQueueUser(queueUserUpdateDTO))
    }

    @PutMapping("/updateStatus")
    fun updateQueueUserStatus(@RequestBody queueUserUpdateStatusDTO: QueueUserUpdateStatusDTO): ResponseEntity<QueueUserDTO>{
        return ResponseEntity.ok(queueUserService.updateQueueUserStatus(queueUserUpdateStatusDTO))
    }

    @PutMapping("/delete/{id}")
    fun deleteQueueUser(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity(queueUserService.deleteQueueUser(id), HttpStatus.NO_CONTENT)
    }

}