package com.example.demo.common.config

import com.example.avro.schema.PrintHelloVo
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory


@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.producer.bootstrap-servers:}")
    private val bootstrapServers: String,
    @Value("\${spring.kafka.properties.schema-registry-url:}")
    private val schemaRegistryUrl: String,
    @Value("\${spring.kafka.producer.acks:}")
    private val acks_config: String,
    @Value("\${spring.kafka.producer.retries:}")
    private val retries_config: Int,
) {
    @Bean
    fun printHelloKafkaProducerContainerFactory(): ProducerFactory<String, PrintHelloVo> {
        val producerConfig = getKafkaProducerConfig()
        return DefaultKafkaProducerFactory(producerConfig)
    }

    @Bean
    fun defaultKafkaProducerContainerFactory(): ProducerFactory<Any, Any> {
        val producerConfig = getKafkaProducerConfig()
        return DefaultKafkaProducerFactory<Any, Any>(producerConfig)
    }

    fun getKafkaProducerConfig(): MutableMap<String, Any> {
        return mutableMapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
            ProducerConfig.ACKS_CONFIG to acks_config,
            ProducerConfig.RETRIES_CONFIG to retries_config,
            KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
        )
    }
}