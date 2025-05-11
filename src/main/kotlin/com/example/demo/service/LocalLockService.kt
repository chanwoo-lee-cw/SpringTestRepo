package com.example.demo.service

import com.example.demo.aop.annotation.LocalLock
import com.example.demo.dto.LocalLockDto
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.lang.Math.random


val logger = KotlinLogging.logger {  }

@Service
class LocalLockService {

    @LocalLock(
        prefix = "localLockFunction",
        key = "userName"
    )
    fun localLockMethod(userName: String) {
        Thread.sleep(random().toLong() * 300L + 100)
        logger.info { "${userName} 프린트" }
    }
}