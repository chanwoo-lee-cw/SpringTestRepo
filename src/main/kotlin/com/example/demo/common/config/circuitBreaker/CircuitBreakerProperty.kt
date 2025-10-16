package com.example.demo.common.config.circuitBreaker

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "resilience4j.circuitbreaker")
data class CircuitBreakerProperty(
    val failureRateThreshold: Float,
    val slowCallDurationThreshold: Long,
    val slowCallRateThreshold: Float,
    val waitDurationInOpenState: Long,
    val minimumNumberOfCalls: Int,
    val slidingWindowSize: Int,
    val permittedNumberOfCallsInHalfOpenState: Int,
) {
}