package com.telemondo.qs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QsBackendApplication

fun main(args: Array<String>) {
	runApplication<QsBackendApplication>(*args)
}
