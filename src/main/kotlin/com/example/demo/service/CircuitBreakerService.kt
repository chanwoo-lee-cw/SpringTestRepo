package com.example.demo.service

import com.example.demo.common.config.circuitBreaker.CircuitBreakerConfiguration
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {

    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(name = CircuitBreakerConfiguration.CB_REDIS, fallbackMethod = "cbTestFallbackMethod")
    fun circuitBreakerTest(value: Boolean): Boolean {
        return when (value) {
            true -> true
            false -> throw RuntimeException("에러 발생")
        }
    }


    fun cbTestFallbackMethod(
        value: Boolean,
        ex: RuntimeException
    ): Boolean {

        logger.info("${ex.message} 서킷 브레이커 작동")
        return when (value) {
            true -> true
            false -> false
        }
    }

}