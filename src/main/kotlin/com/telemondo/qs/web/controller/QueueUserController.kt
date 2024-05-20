package com.telemondo.qs.web.controller

import com.telemondo.qs.dto.QueueUserCreateDTO
import com.telemondo.qs.dto.QueueUserDTO
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queueUser")
class QueueUserController(
    private val queueUserService: QueueUserService
) {
    data class pageableObject (
        val startingPage: Int,
        val pageSize: Int
    )

    @GetMapping
    fun getQueueUsers(@RequestBody pageableObject: pageableObject): ResponseEntity<List<QueueUserDTO>>{
        return ResponseEntity.ok(queueUserService.getQueueUsers(pageableObject.startingPage, pageableObject.pageSize))
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
    fun updateQueueUser(@RequestBody queueUserDTO: QueueUserDTO): ResponseEntity<QueueUserDTO>{
        return ResponseEntity.ok(queueUserService.updateQueueUser(queueUserDTO))
    }

    @PutMapping("/updateStatus")
    fun updateQueueUserStatus(@RequestBody queueUserUpdateStatusDTO: QueueUserUpdateStatusDTO): ResponseEntity<QueueUserDTO>{
        return ResponseEntity.ok(queueUserService.updateQueueUserStatus(queueUserUpdateStatusDTO))
    }

    @DeleteMapping("/{id}")
    fun deleteQueueUser(@PathVariable id: String): ResponseEntity<Unit>{
        return ResponseEntity(queueUserService.deleteQueueUser(id), HttpStatus.NO_CONTENT)
    }

}