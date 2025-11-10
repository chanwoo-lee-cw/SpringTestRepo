package com.example.api.controller


import com.example.common.aop.annotation.MeasureRuntime
import com.example.domain.service.HelloService
import com.example.kafka.dto.HelloRequestDto
import com.example.kafka.dto.HelloResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1")
class HelloTestController(
    private val helloService: HelloService
) {

    @GetMapping()
    fun helloWorld(): HelloResponseDto {
        return HelloResponseDto("success")
    }


    @GetMapping("/print-person")
    fun PrintPerson(@RequestBody request: HelloRequestDto): HelloResponseDto {
        return helloService.printPersonName(request)
    }

    @MeasureRuntime
    @GetMapping("/print-hello-by-kafka")
    fun printHello(): Unit {
        return helloService.printHelloKafka()
    }

}