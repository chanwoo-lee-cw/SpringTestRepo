package com.example.demo.common.config.circuitBreaker

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "resilience4j.circuitbreaker")
class CircuitBreakerProperty(
    val failureRateThreshold: Float = Float.MIN_VALUE,
    val slowCallDurationThreshold: Long = Long.MIN_VALUE,
    val slowCallRateThreshold: Float = Float.MIN_VALUE,
    val waitDurationInOpenState: Long = Long.MIN_VALUE,
    val minimumNumberOfCalls: Int = Int.MIN_VALUE,
    val slidingWindowSize: Int = Int.MIN_VALUE,
    val permittedNumberOfCallsInHalfOpenState: Int = Int.MIN_VALUE,
) {
}