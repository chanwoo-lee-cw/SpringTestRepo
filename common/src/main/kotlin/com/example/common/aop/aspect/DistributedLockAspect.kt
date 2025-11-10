package com.example.common.aop.aspect

import com.example.common.aop.annotation.UserDistributedLock
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component


@Aspect
@Component
class DistributedLockAspect(
    private val redissonClient: RedissonClient
) {
    private val logger = KotlinLogging.logger {  }

    @Around("@annotation(com.example.common.aop.annotation.UserDistributedLock)")
    fun localLock(joinPoint : ProceedingJoinPoint): Any {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(UserDistributedLock::class.java) ?: throw RuntimeException("락이 없어요.")

        val key = "${distributedLock.lockType}::${joinPoint.args[signature.parameterNames.indexOf(distributedLock.key)]}"

        val lock = redissonClient.getLock(key)
        try {
            logger.info { "${key} 로깅 시작" }
            val available = lock.tryLock(distributedLock.waitTime, distributedLock.timeOut)
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