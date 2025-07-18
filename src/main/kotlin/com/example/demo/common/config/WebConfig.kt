package com.example.demo.common.config

import com.example.demo.interceptor.TestInterceptor
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig(
    val testInterceptor: TestInterceptor,
) : WebMvcConfigurer {
    private val log = KotlinLogging.logger {}

    override fun addInterceptors(registry: InterceptorRegistry) {
        log.info { "testInterceptor 등록" }
        registry.addInterceptor(testInterceptor)
            .addPathPatterns("/api/**")
    }
}