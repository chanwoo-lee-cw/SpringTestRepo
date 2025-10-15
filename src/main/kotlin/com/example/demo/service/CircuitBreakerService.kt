package com.example.demo.service

import com.example.demo.common.config.circuitBreaker.CircuitBreakerConfiguration

import org.springframework.stereotype.Service
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
//import io.github.resilience4j.retry.annotation.Retry
//import io.github.resilience4j.timelimiter.annotation.TimeLimiter
//import org.springframework.stereotype.Service
//import org.springframework.web.client.RestClient
//import java.util.concurrent.CompletableFuture

@Service
class CircuitBreakerService {

//    @CircuitBreaker(name = CircuitBreakerConfiguration.CB_REDIS)
//    fun find(storeId: String, productId: String): Stock {
//        return redisManager.findByKey(StockKey(storeId, productId))   // REDIS 조회
//    }

}