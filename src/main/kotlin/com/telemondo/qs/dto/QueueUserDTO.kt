package com.telemondo.qs.dto


import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

data class QueueUserDTO(
    var id: String,
    var ticketNum: String,
    val counterType: CounterTypeDTO,
//    used a CounterForQueueUserDTO for QueueUserDTO to prevent infinite recursion because
//    if Counter would be QueueUser's counter property, it will have the currentCustomer property
//    which would require QueueUser object again with its counter property which will have another c
//    currentCustomer property again and so on
    val counter: CounterForQueueUserDTO?,
//    -1 = cancelled, 1 = waiting, 2 = on-counter, 3 = complete
    val status: Int = 1,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class QueueUserCreateDTO(
    val counterTypeId: String,
    val counterId : String?,
//    val counter: CounterDTO?,
    val status: Int = 1,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class QueueUserUpdateStatusDTO(
    val id: String,
    val status: Int = 1,
)