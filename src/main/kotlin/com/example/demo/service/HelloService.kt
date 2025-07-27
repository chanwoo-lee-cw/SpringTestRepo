package com.example.demo.service

import com.example.demo.aop.annotation.MeasureRuntime
import com.example.demo.dto.HelloRequestDto
import com.example.demo.dto.HelloResponseDto
import org.springframework.stereotype.Service

@Service
class HelloService {

    @MeasureRuntime
    fun printPersonName(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        print("${helloRequestDto.name} : ${helloRequestDto.age}")
        return HelloResponseDto("success")
    }
}