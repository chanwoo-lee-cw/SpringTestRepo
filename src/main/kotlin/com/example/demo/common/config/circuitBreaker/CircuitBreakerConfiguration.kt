package com.example.demo.common.config.circuitBreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CircuitBreakerConfiguration(
    val circuitBreakerProperty: CircuitBreakerProperty
) {

    companion object {
        const val CB_REDIS: String = "CB_REDIS"
    }

    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.ofDefaults()
    }

    @Bean
    fun circuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(circuitBreakerProperty.failureRateThreshold)
            .slowCallDurationThreshold(Duration.ofMillis(circuitBreakerProperty.slowCallDurationThreshold))
            .slowCallRateThreshold(circuitBreakerProperty.slowCallRateThreshold)
            .waitDurationInOpenState(Duration.ofMillis(circuitBreakerProperty.waitDurationInOpenState))
            .minimumNumberOfCalls(circuitBreakerProperty.minimumNumberOfCalls)
            .slidingWindowSize(circuitBreakerProperty.slidingWindowSize)
            .ignoreExceptions(Exception::class.java)   // 화이트리스트로 서킷 오픈 기준 관리
            .permittedNumberOfCallsInHalfOpenState(circuitBreakerProperty.permittedNumberOfCallsInHalfOpenState)
            .build()
    }

    @Bean
    fun redisCircuitBreaker(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
        return circuitBreakerRegistry.circuitBreaker(
            CB_REDIS,
            circuitBreakerConfig()
        )
    }
}