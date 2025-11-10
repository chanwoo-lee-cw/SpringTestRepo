package com.example.common.aop.aspect

import com.example.common.aop.annotation.UserLocalLock
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock


@Aspect
@Component
class LocalLockAspect {

    private val logger = KotlinLogging.logger {  }
    private val lockMap = ConcurrentHashMap<String, ReentrantLock>()

    @Around("@annotation(com.example.common.aop.annotation.UserLocalLock)")
    fun localLock(joinPoint : ProceedingJoinPoint): Any {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val localLock = method.getAnnotation(UserLocalLock::class.java) ?: throw RuntimeException("락이 없어요.")

        val key = "${localLock.lockType}::${joinPoint.args[signature.parameterNames.indexOf(localLock.key)]}"

        val lock = lockMap.computeIfAbsent(key) { ReentrantLock(true) } // 로 하면 FIFO 순서로 Request 처리하게 됨.
        try {
            logger.info { "${key} 로깅 시작" }
            val available = lock.tryLock(localLock.waitTime, localLock.timeOut)
            if(!available) {
                logger.info { "${method}, key = ${key}, Fail : 잠금 시도 시간을 초과했습니다. " }
                throw RuntimeException("잠금 시도 시간을 초과했습니다.")
            }
            logger.info { "${method} 시작, key = ${key}" }
            val result = joinPoint.proceed()
            logger.info { "${method} 완료, key = ${key}" }
            return result
        } finally {
            lock.unlock()
            logger.info { "${key} unLock" }
        }

    }

}