package com.example.api.controller


import com.example.domain.service.DistributedLockService
import com.example.domain.service.LocalLockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/lock")
class LockController(
    val localLockService: LocalLockService,
    val distributedLockService: DistributedLockService,
) {

    @GetMapping("/{userName}/local-lock")
    fun localLock(@PathVariable userName: String): Unit {
        localLockService.waitJob(
            userName = userName
        )
    }

    @GetMapping("/{userName}/distributed-lock")
    fun distributedLock(@PathVariable userName: String): Unit {
        distributedLockService.distributedLockWaitJob(
            userName = userName
        )
    }
}