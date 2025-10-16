package com.example.demo.service

import com.example.demo.common.config.circuitBreaker.CircuitBreakerConfiguration
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(
    properties = [
        "kafka.enabled=false"
    ]
)
@ActiveProfiles("test")
class CircuitBreakerServiceTest @Autowired constructor(
    private val service: CircuitBreakerService,
    private val registry: CircuitBreakerRegistry
) {

    @Test
    @DisplayName("value=true면 정상 리턴(true)")
    fun returnsTrueWhenValueIsTrue() {
        val result = service.circuitBreakerTest(true)
        assertThat(result).isTrue()
    }

    @Test
    @DisplayName("value=false면 예외→폴백 호출되어 false 리턴")
    fun returnsFalseViaFallbackWhenValueIsFalse() {
        val result = service.circuitBreakerTest(false)

        assertThat(result).isFalse()
    }

}