//package com.telemondo.qs
//
//import io.nats.client.Connection;
//import io.nats.client.Message;
//import org.springframework.stereotype.Component;
//
//@Component
//class NatsListener(private val connection: Connection) {
//
//    init {
//        val dispatcher = connection.createDispatcher { message: Message? ->
//            handleMessage(message)
//        }
//        dispatcher.subscribe("Queue User")
//    }
//
//    private fun handleMessage(message: Message?) {
//        if (message != null) {
//            val data = String(message.data)
//            // Process the message data here
//        }
//    }
//}
