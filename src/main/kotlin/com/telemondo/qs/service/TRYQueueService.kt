package com.telemondo.qs.service

interface TRYQueueService<T> {
    val count: Int

    val isEmpty: Boolean

    fun peek(): T?

    fun enqueue(element: T): Boolean

    fun dequeue(): T?
}