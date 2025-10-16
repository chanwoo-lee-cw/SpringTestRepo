//package com.example.demo
//
//import com.example.demo.dto.HelloRequestDto
//import com.example.demo.service.HelloService
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.context.SpringBootTest
//
//class HelloServiceTest : BehaviorSpec({
//
//	val helloService = HelloService()
//
//	Given("몰라요") {
//		When("사람 이름이 있을때") {
//			val helloRequestMock = mockk<HelloRequestDto>(relaxed = true)
//
//			val result = helloService.printPersonName(helloRequestMock)
//
//			Then("성공했는지 확인한다.") {
//				result.success shouldBe "success"
//			}
//		}
//	}
//})