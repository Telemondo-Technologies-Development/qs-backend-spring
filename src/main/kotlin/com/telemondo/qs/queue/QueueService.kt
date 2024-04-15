package com.telemondo.qs.queue

interface QueueService<T> {
    val count: Int

    val isEmpty: Boolean

    fun peek(): T?

    fun enqueue(element: T): Boolean

    fun dequeue(): T?
}