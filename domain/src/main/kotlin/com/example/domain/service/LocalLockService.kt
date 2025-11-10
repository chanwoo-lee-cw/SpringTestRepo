package com.example.domain.service

import com.example.common.aop.annotation.LockType
import com.example.common.aop.annotation.UserLocalLock
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class LocalLockService {

    val logger = KotlinLogging.logger { }

    @UserLocalLock(
        lockType = LockType.WaitLock,
        key = "userName"
    )
    fun waitJob(userName: String): Int {
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
//        Thread.sleep(random().toLong() * 300L + 100)
        Thread.sleep(5000)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }


    @UserLocalLock(
        lockType = LockType.ThrowError,
        key = "userName"
    )
    fun throwError(userName: String): Int {
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
        Thread.sleep(5000)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }
}