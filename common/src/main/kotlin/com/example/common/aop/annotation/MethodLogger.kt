package com.example.common.aop.annotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MethodLogger(
    val loggingType: LoggingType = LoggingType.DEBUG
)
