package com.telemondo.qs


import com.telemondo.qs.queue.QueueServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication

class QueueManagementSystem{
}

fun main(args: Array<String>) {
	runApplication<QueueManagementSystem>(*args)

//	val queueNumber = QueueServiceImpl<Int>()
//
//	queueNumber.enqueue(1)
//
//	val currentQueueNumber = queueNumber.count
//	println(currentQueueNumber)

}
