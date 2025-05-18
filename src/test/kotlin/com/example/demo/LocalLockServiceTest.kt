package com.example.demo

import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Collections
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test


@SpringBootTest
class LocalLockServiceTest @Autowired constructor(
    val localLockServiceForTest: LocalLockServiceForTest
) {

    private val log = KotlinLogging.logger {}

    @Test
    fun `timeWaitThreeSeconds 함수가 정상 작동하는지 확인`() {
        localLockServiceForTest.timeWaitThreeSeconds("peter")
    }

    @Test
    fun `2개의 요청을 보냈을때 timeOut이 걸렸을때 실패하는지 확인`() {
        val userName = "peter"

        val executor = Executors.newFixedThreadPool(2)

        val tasks = List(2) {
            Callable {
                try {
                    val result = localLockServiceForTest.timeWaitThreeSeconds(userName)
                    log.info { "쓰레드 :: 성공" }
                    result
                } catch (e: Exception) {
                    log.info { "쓰레드 :: 실패" }
                    -1
                }
            }
        }
        val futures = executor.invokeAll(tasks)
        executor.shutdown()

        var successCount = 0
        var failCount = 0
        for (feature in futures) {
            val result = feature.get()
            if (result == 1) successCount += 1
            else failCount += 1
        }

        successCount shouldBe 1
        failCount shouldBe 1
    }


    @Test
    fun `timeWaitSixSeconds 함수가 정상 작동하는지 확인`() {
        localLockServiceForTest.timeWaitSixSeconds("peter")
    }

    @Test
    fun `2개의 요청을 보냈을때 제대로 작동하는지 확인`() {
        val userName = "peter"

        val executor = Executors.newFixedThreadPool(2)

        val tasks = List(2) {
            Callable {
                try {
                    val result = localLockServiceForTest.timeWaitSixSeconds(userName)
                    log.info { "쓰레드 :: 성공" }
                    result
                } catch (e: Exception) {
                    log.info { "쓰레드 :: 실패" }
                    -1
                }
            }
        }
        val futures = executor.invokeAll(tasks)
        executor.shutdown()

        var successCount = 0
        var failCount = 0
        for (feature in futures) {
            val result = feature.get()
            if (result == 1) successCount += 1
            else failCount += 1
        }

        successCount shouldBe 2
        failCount shouldBe 0
    }

    @Test
    fun `요청을 여러개 보냈을때 순서대로 작동하는지 확인`() {
        val userName = "peter"

        val executor = Executors.newFixedThreadPool(5)
        val executionOrder = Collections.synchronizedList(mutableListOf<Pair<String, Int>>())

        val tasks = List(10) {index ->
            Callable {
                try {
                    Thread.sleep(50L * index)
                    executionOrder += "start" to index
                    localLockServiceForTest.timeWaitRandomSeconds(userName)
                    executionOrder += "end" to index
                    index
                } catch (e: Exception) {
                    log.info { "쓰레드 :: 실패" }
                    executionOrder += "end" to index
                    index
                }
            }
        }
        val futures = executor.invokeAll(tasks)
        executor.shutdown()

        val startOrder = executionOrder.filter { it.first == "start" }.map { it.second }
        val endOrder = executionOrder.filter { it.first == "end" }.map { it.second }

        startOrder.size shouldBe 10
        endOrder shouldBe startOrder
    }
}