package com.example.demo.aop.annotation

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserLock(
    val lockType: LockType,
    val key: String,
    val timeOut: TimeUnit = TimeUnit.SECONDS,
    val waitTime: Long = 5
)