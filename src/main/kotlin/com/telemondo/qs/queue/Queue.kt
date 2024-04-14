package com.telemondo.qs.queue

interface Queue<T> {
    val count: Int

    val isEmpty: Boolean

    fun peek(): T?

    fun enqueue(element: T): Boolean

    fun dequeue(): T?
}