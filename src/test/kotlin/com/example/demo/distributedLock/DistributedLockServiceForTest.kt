package com.example.demo.distributedLock

import com.example.demo.aop.annotation.UserDistributedLock
import org.springframework.stereotype.Service

@Service
class DistributedLockServiceForTest {

    @UserDistributedLock
    fun test() {

    }
}