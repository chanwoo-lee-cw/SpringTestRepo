package com.example.common.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class TestFilter() : Filter {

    private val log = KotlinLogging.logger {}

    override fun init(filterConfig: FilterConfig?) {
//        log.info { "Filter init" }
        super.init(filterConfig)
    }

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        filterChain: FilterChain,
    ) {
//        log.info { "doFilter start" }
        filterChain.doFilter(request, response)
//        log.info { "doFilter end" }
    }

    override fun destroy() {
//        log.info { "Filter Destroy" }
        super.destroy()
    }

}