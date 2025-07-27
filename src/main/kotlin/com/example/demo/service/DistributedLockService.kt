package com.example.demo.service

import com.example.demo.aop.annotation.LockType
import com.example.demo.aop.annotation.UserDistributedLock
import org.springframework.stereotype.Service

@Service
class DistributedLockService {


    @UserDistributedLock(
        lockType = LockType.WaitLock,
        key = "userName"
    )
    fun distributedLockWaitJob(userName: String): Int {
//        logger.info { "Success :: 분산락 Lock 테스트 ${userName} Start" }
        Thread.sleep(5000)
//        logger.info { "Success :: 분산락 Lock 테스트 ${userName} End" }
        return 1
    }
}