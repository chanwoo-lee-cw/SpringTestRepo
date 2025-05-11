//package com.example.demo
//
//
//import com.example.demo.service.LocalLockService
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldNotBe
//import mu.KotlinLogging
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.util.concurrent.Callable
//import java.util.concurrent.Executors
//import java.util.concurrent.TimeUnit
//
//
//@ExtendWith(SpringExtension::class)
//@SpringBootTest
//class LocalLockServiceTest(
//    @Autowired val localLockService: LocalLockService
//)  : BehaviorSpec({
//
//    val loggger = KotlinLogging.logger {}
//
//    given("여러 개의 같은 요청을 보내느 케이스") {
//        val executor = Executors.newFixedThreadPool(200)
//
//        val userName = "sameUser"
//        `when`("여러개의 같은 요청이 들어왔을 때") {
//            val tasks = List(10) {
//                Callable {
//                    try {
//                        localLockService.localLockMethod(userName)
//                    } catch (e: Exception) {
//                        loggger.error { "❌ 예외 발생: ${e.message}" }
//                        return@Callable -1 // 실패 시 -1 반환
//                    }
//                }
//            }
//            then("락이 걸리는가?") {
//                val futures = executor.invokeAll(tasks, 10, TimeUnit.SECONDS)
//                val (successCount, failCount) = futures.partition{ it -> it.get() == 1}
//                failCount.count() shouldNotBe 0
//            }
//        }
//    }
//})