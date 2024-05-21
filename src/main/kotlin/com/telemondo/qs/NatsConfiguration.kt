package com.telemondo.qs

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//nats-server.exe
@Configuration
class NatsConfig {
    @Bean
    fun natsConnection(): Connection {
        return Nats.connect("nats://localhost:8080") // Adjust the URL as per your NATS server configuration
    }
}