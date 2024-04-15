package com.telemondo.qs.queue

class QueueServiceImpl<T> : QueueService<T> {

    private val storage = arrayListOf<T>()

    override val count: Int
        get() = storage.size

    override val isEmpty: Boolean
        get() = count == 0

    override fun enqueue(element: T): Boolean {
        return storage.add(element)
    }

    override fun dequeue(): T? {
        return if(isEmpty) null else storage.removeAt(0)
    }

    override fun peek(): T? = storage.getOrNull(0)
}




