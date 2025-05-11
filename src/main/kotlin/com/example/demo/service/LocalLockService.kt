package com.example.demo.service

import com.example.demo.aop.annotation.LocalLock
import mu.KotlinLogging
import org.springframework.stereotype.Service


val logger = KotlinLogging.logger {  }

@Service
class LocalLockService {

    @LocalLock(
        prefix = "localLockFunction",
        key = "userName"
    )
    fun localLockMethod(userName: String): Int {
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
//        Thread.sleep(random().toLong() * 300L + 100)
        Thread.sleep(5000)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }
}