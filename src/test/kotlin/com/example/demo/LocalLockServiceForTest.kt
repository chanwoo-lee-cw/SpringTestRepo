package com.example.demo

import com.example.demo.aop.annotation.LockType
import com.example.demo.aop.annotation.UserLock
import com.example.demo.service.logger
import org.springframework.stereotype.Service
import java.util.Random

@Service
class LocalLockServiceForTest {


    @UserLock(
        lockType = LockType.WaitLock,
        key = "userName",
        waitTime = 3,
    )
    fun timeWaitThreeSeconds(userName: String): Int {
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
        Thread.sleep(5000)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }

    @UserLock(
        lockType = LockType.WaitLock,
        key = "userName",
        waitTime = 6,
    )
    fun timeWaitSixSeconds(userName: String): Int {
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
        Thread.sleep(5000)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }

    @UserLock(
        lockType = LockType.WaitLock,
        key = "userName",
        waitTime = 10,
    )
    fun timeWaitRandomSeconds(userName: String): Int {
        val random = Random()
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} Start" }
        Thread.sleep(random.nextInt(5) * 1000L)
        logger.info { "Success :: 로컬 Lock 테스트 ${userName} End" }
        return 1
    }
}