package com.example.demo.service

import com.example.demo.dto.HelloRequestDto
import com.example.demo.dto.HelloResponseDto
import org.springframework.stereotype.Service

@Service
class HelloService {

    fun printPersonName(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        print("${helloRequestDto.name} : ${helloRequestDto.age}")
        return HelloResponseDto("success")
    }
}