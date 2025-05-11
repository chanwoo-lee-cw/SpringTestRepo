package com.example.demo.controller

import com.example.demo.dto.LocalLockDto
import com.example.demo.service.LocalLockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/lock")
class LockController(
    val localLockService: LocalLockService
) {

    @GetMapping("/{userName}/local-lock")
    fun localLock(@PathVariable userName: String): Unit {
        return localLockService.localLockMethod(
            userName = userName
        )
    }
}