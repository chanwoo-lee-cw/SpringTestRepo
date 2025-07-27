package com.example.demo.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class OnceTestRequestFilter: OncePerRequestFilter() {
    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        log.info { "this is OnceTestRequestFilter Start" }
        filterChain.doFilter(request, response)
//        log.info { "this is OnceTestRequestFilter end" }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // 아무것도 작성하지 않는다면 기본 값이 False이다
//        log.info { "this is shouldNotFilter Start" }
        return false
    }
}