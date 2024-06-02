package com.telemondo.qs.dto


import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

data class QueueUserDTO(
    var id: String,
    var ticketNum: String,
//    1 = regular, 2 = non-regular
    val customerType: Int,
    val counterType: CounterTypeDTO,
//    used a CounterForQueueUserDTO for QueueUserDTO to prevent infinite recursion because
//    if Counter would be QueueUser's counter property, it will have the currentCustomer property
//    which would require QueueUser object again with its counter property which will have another
//    currentCustomer property again and so on
    val counter: CounterForQueueUserDTO?,
//   -3 = deleted, -2 = cancelled, -1 = no-show, 1 = waiting, 2 = on-counter, 3 = complete
    val status: Int = 1,
    val createdAt: Instant,
    val entertainedAt: Instant,
    val estimatedWaitTime: String,
    val updatedAt: Instant
)

data class QueueUserCreateDTO(
    val customerType: Int,
    val counterTypeId: String,
    val status: Int = 1
)

data class QueueUserUpdateStatusDTO(
    val id: String,
    val status: Int,
)

data class QueueUserUpdateDTO(
    var id: String,
    var ticketNum: String?,
    val customerType: Int?,
    val counterTypeId: String?,
    val counterId: String?,
    val status: Int?
)