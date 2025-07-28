package com.example.demo.producer


import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.Message
import org.springframework.stereotype.Component


@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {

    val log = KotlinLogging.logger { }

    fun send(message: Message<*>) {
        val future = kafkaTemplate.send(message)
        try {
            future.get()
        } catch (e: Exception) {
            log.error { "[KafkaProducer.send] ${e} :: header - ${message.headers}, body - ${message.payload}" }
        }
    }
}
