package com.example.demo.producer

import com.example.avro.schema.PrintHelloVo
import com.example.demo.aop.annotation.MethodLogger
import com.example.demo.dto.PrintHelloDto
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


@Component
class PrintHelloProducer(
    private val kafkaProducer: KafkaProducer,
    @Value("\${application.kafka-topic.print-hello:}")
    private val printHelloTopic: String,
    @Value("\${spring.application.name:}")
    private val applicationName: String,
) {

    private val log = KotlinLogging.logger { }

    @MethodLogger
    fun sendHelloPrint(dto: PrintHelloDto) {
        log.info { "[PrintHelloProducer.sendHelloPrint] publish printHelloVo, topic - ${printHelloTopic}, dto-${dto}" }
        val uuid = UUID.randomUUID().toString()
        val key = "from-${applicationName}-${uuid}"

        kafkaProducer.send(
            MessageBuilder
                .withPayload(
                    PrintHelloVo.newBuilder()
                        .setSender(applicationName)
                        .setPrintMessage(dto.printMessage)
                        .setPublishDateTime(Instant.now().toString())
                        .build()
                )
                .setHeader(KafkaHeaders.TOPIC, printHelloTopic)
                .setHeader(KafkaHeaders.KEY, key)
                .build()
        )
    }
}