package com.example.demo.aop.aspect

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component


@Aspect
@Component
class LoggingAspect {

    val logger = KotlinLogging.logger {  }

    @Before("execution(* com.example.demo.controller..*(..))")
    fun logBefore(joinPoint: JoinPoint) {
        logger.info { "[${joinPoint.signature.name}] Start method" }
    }

    @AfterThrowing("execution(* com.example.demo.service..*(..))")
    fun logAfterException(joinPoint: JoinPoint) {
        logger.info { "[${joinPoint.signature.name}] throw Error, args : ${joinPoint.args}" }
    }
}