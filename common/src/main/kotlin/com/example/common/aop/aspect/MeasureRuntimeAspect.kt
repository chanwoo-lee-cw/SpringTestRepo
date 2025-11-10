package com.example.common.aop.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


@Aspect
@Component
class MeasureRuntimeAspect {
    val log = KotlinLogging.logger { }

    @Around("@annotation(com.example.demo.aop.annotation.MeasureRuntime)")
    fun measureRuntime(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis();
        val result = joinPoint.proceed()
        val endTime = System.currentTimeMillis();
        log.info { "[${joinPoint.signature.name}] 실행 시간 : ${endTime - startTime}ms" }
        return result
    }

}