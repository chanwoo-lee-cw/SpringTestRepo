package com.example.demo.service

import com.example.demo.common.config.circuitBreaker.CircuitBreakerConfiguration
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {

    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(name = CircuitBreakerConfiguration.CB_TEST, fallbackMethod = "cbTestFallbackMethod")
    fun circuitBreakerTest(value: Boolean): Boolean {
        return when (value) {
            true -> true
            false -> throw RuntimeException("에러 발생")
        }
    }

    /**
     * fallbackMethod를 "cbTestFallbackMethod" 해놓은 메소드에서  RuntimeException 예외가 발생하면 이곳에서 처리한다.
     */
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

    /**
     * fallbackMethod를 "cbTestFallbackMethod" 해놓은 메소드에서  Exception 예외가 발생하면 이곳에서 처리한다.
     */
    fun cbTestFallbackMethod(
        value: Boolean,
        ex: Exception
    ): Boolean {
        logger.info("${ex.message} 서킷 브레이커 작동")
        return when (value) {
            true -> true
            false -> false
        }
    }

}