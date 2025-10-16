//import com.example.demo.aop.annotation.LockType
//import com.example.demo.aop.annotation.UserLocalLock
//import com.example.demo.aop.aspect.DistributedLockAspect
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.reflect.MethodSignature
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.Executors
//import kotlin.jvm.java
//import kotlin.test.Test
//import kotlin.test.assertTrue
//
///**
// * 챗 GPT가 만든 락 테스트 코트
// */
//
//class LocalLockAspectTest {
//
//    private val aspect = DistributedLockAspect()
//
//
//    @Test
//    fun `lock이 정상적으로 작동하는지 확인`() {
//        // Arrange
//        val mockJoinPoint = mock(ProceedingJoinPoint::class.java)
//        val methodSignature = mock(MethodSignature::class.java)
//        val mockMethod = TestService::class.java.getMethod("mockedMethod", String::class.java)
//
//        val args = arrayOf("userA")
//
//        `when`(mockJoinPoint.signature).thenReturn(methodSignature)
//        `when`(methodSignature.method).thenReturn(mockMethod)
//        `when`(methodSignature.parameterNames).thenReturn(arrayOf("userName"))
//        `when`(mockJoinPoint.args).thenReturn(args)
//
//        val wasCalled = mutableListOf<Boolean>()
//        `when`(mockJoinPoint.proceed()).thenAnswer {
//            wasCalled.add(true)
//            null
//        }
//
//        // Act
//        aspect.localLock(mockJoinPoint)
//
//        // Assert
//        assertTrue(wasCalled.isNotEmpty(), "proceed()가 호출되지 않았습니다.")
//    }
//
//
//    @Test
//    fun `동일 키로 두 번 호출시 첫 번째는 성공하고 두 번째는 타임아웃`() {
//        // given
//        val method = TestService::class.java.getMethod("longRunningMethod", String::class.java)
//        val signature = mock(MethodSignature::class.java)
//        `when`(signature.method).thenReturn(method)
//        `when`(signature.parameterNames).thenReturn(arrayOf("userName"))
//
//        val latch = CountDownLatch(2)
//        val results = mutableListOf<String>()
//        val lock = Any()
//
//        val executor = Executors.newFixedThreadPool(2)
//
//        // 첫 번째 스레드 (락을 오래 잡음)
//        executor.submit {
//            val joinPoint = mock(ProceedingJoinPoint::class.java).apply {
//                `when`(this.signature).thenReturn(signature)
//                `when`(this.args).thenReturn(arrayOf("userA"))
//                `when`(this.proceed()).thenAnswer {
//                    Thread.sleep(3000) // 오래 잡고 있음
//                    null
//                }
//            }
//
//            try {
//                aspect.localLock(joinPoint)
//                synchronized(lock) { results.add("A success") }
//            } catch (e: Exception) {
//                synchronized(lock) { results.add("A fail: ${e.message}") }
//            } finally {
//                latch.countDown()
//            }
//        }
//
//        // 두 번째 스레드 (락 타임아웃 짧게)
//        executor.submit {
//            Thread.sleep(100) // 첫 번째가 먼저 잡게 함
//            val joinPoint = mock(ProceedingJoinPoint::class.java).apply {
//                `when`(this.signature).thenReturn(signature)
//                `when`(this.args).thenReturn(arrayOf("userA"))
//                `when`(this.proceed()).thenAnswer { null }
//            }
//
//            try {
//                aspect.localLock(joinPoint)
//                synchronized(lock) { results.add("B success") }
//            } catch (e: Exception) {
//                synchronized(lock) { results.add("B fail: ${e.message}") }
//            } finally {
//                latch.countDown()
//            }
//        }
//
//        // wait
//        latch.await()
//
//        // then
//        println("결과: $results")
//        assertTrue(results.contains("A success"))
//        assertTrue(results.any { it.startsWith("B fail: 잠금 시도 시간을 초과했습니다") })
//    }
//
//    class TestService {
//        @UserLocalLock(lockType = LockType.WaitLock, key = "userName", waitTime = 1)
//        fun mockedMethod(userName: String): Int = 42
//
//        @UserLocalLock(lockType = LockType.WaitLock, key = "userName", waitTime = 1)
//        fun longRunningMethod(userName: String): Int = 42
//    }
//}
