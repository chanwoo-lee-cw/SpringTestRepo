package com.example.kafka.producer


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
            val response = future.get()
            log.info { "[KafkaProducer.send] topic - ${response.recordMetadata.topic()} :: partition - ${response.recordMetadata.partition()}, offset - ${response.recordMetadata.offset()}" }
        } catch (e: Exception) {
            log.error { "[KafkaProducer.send] ${e} :: header - ${message.headers}, body - ${message.payload}" }
        }
    }
}
