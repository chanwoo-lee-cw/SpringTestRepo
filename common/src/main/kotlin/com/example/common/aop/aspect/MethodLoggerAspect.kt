package com.example.common.aop.aspect

import com.example.common.aop.annotation.LoggingType
import com.example.common.aop.annotation.MethodLogger
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component


@Aspect
@Component
class MethodLoggerAspect {

    val log = KotlinLogging.logger { }

    @Pointcut("@annotation(com.example.common.aop.annotation.MethodLogger)")
    fun methodLoggerPointcut() {
    }

    @Before("methodLoggerPointcut()")
    fun methodBeforeLogger(jointPoint: JoinPoint) {
        val signature = jointPoint.signature as MethodSignature
        val methodLogger =
            signature.method.getAnnotation(MethodLogger::class.java) ?: throw RuntimeException("로깅 타입을 얻지 못했습니다.")

        val startMessege = "[${signature.method.name}] Start :: args - ${jointPoint.args}"
        loggingMessage(methodLogger.loggingType, startMessege)
    }

    @After("methodLoggerPointcut()")
    fun methodAfterLogger(jointPoint: JoinPoint) {
        val signature = jointPoint.signature as MethodSignature
        val methodLogger =
            signature.method.getAnnotation(MethodLogger::class.java) ?: throw RuntimeException("로깅 타입을 얻지 못했습니다.")

        val endMessage = "[${signature.method.name}] End"
        loggingMessage(methodLogger.loggingType, endMessage)
    }


    @AfterThrowing("methodLoggerPointcut()", throwing = "exception")
    fun logAfterException(joinPoint: JoinPoint, exception: Exception) {
        log.error { "[${joinPoint.signature.name}] throw ${exception}, args : ${joinPoint.args}" }
    }


    private fun loggingMessage(loggingType: LoggingType, message: String) {
        when (loggingType) {
            LoggingType.DEBUG -> log.debug { message }
            LoggingType.INFO -> log.info { message }
            LoggingType.WARNING -> log.warn { message }
            LoggingType.ERROR -> log.warn { message }
        }
    }

}