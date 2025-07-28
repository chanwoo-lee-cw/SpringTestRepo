package com.example.demo.service

import com.example.demo.aop.annotation.MeasureRuntime
import com.example.demo.dto.HelloRequestDto
import com.example.demo.dto.HelloResponseDto
import com.example.demo.dto.PrintHelloDto
import com.example.demo.producer.PrintHelloProducer
import org.springframework.stereotype.Service

@Service
class HelloService(
    val printHelloProducer: PrintHelloProducer,
) {

    @MeasureRuntime
    fun printPersonName(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        print("${helloRequestDto.name} : ${helloRequestDto.age}")
        return HelloResponseDto("success")
    }


    fun printHelloKafka() {
        printHelloProducer.sendHelloPrint(
            PrintHelloDto(
                "안녕하세요"
            )
        )
    }
}