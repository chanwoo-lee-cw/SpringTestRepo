package com.example.common.config.circuitBreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@EnableConfigurationProperties(CircuitBreakerProperty::class)
class CircuitBreakerConfiguration(
    val circuitBreakerProperty: CircuitBreakerProperty
) {

    companion object {
        // 관리할 서킷 브레이커 이름
        const val CB_TEST: String = "CB_TEST"
    }

    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.ofDefaults()
    }

    /**
     * 기본 서킷 브레이커 설정
     */
    @Bean
    fun circuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(circuitBreakerProperty.failureRateThreshold)
            .slowCallDurationThreshold(Duration.ofMillis(circuitBreakerProperty.slowCallDurationThreshold))
            .slowCallRateThreshold(circuitBreakerProperty.slowCallRateThreshold)
            .waitDurationInOpenState(Duration.ofMillis(circuitBreakerProperty.waitDurationInOpenState))
            .minimumNumberOfCalls(circuitBreakerProperty.minimumNumberOfCalls)
            .slidingWindowSize(circuitBreakerProperty.slidingWindowSize)
//            .ignoreExceptions(RuntimeException::class.java)   // 화이트리스트로 서킷 오픈 기준 관리
            .permittedNumberOfCallsInHalfOpenState(circuitBreakerProperty.permittedNumberOfCallsInHalfOpenState)
            .build()
    }


    /**
     * 서킷 브레이커 테스트 전용으로 사용할 서킷 브레이커
     */
    @Bean
    fun testCircuitBreaker(
        circuitBreakerRegistry: CircuitBreakerRegistry
    ): CircuitBreaker {
        val defaultCircuitBreakerConfig = circuitBreakerConfig()
        return circuitBreakerRegistry.circuitBreaker(
            CB_TEST,
            CircuitBreakerConfig
                .from(defaultCircuitBreakerConfig)
                .apply {
                    CircuitBreakerConfig.custom()
                        .failureRateThreshold(circuitBreakerProperty.failureRateThreshold)
                }
                .build()
        )
    }
}