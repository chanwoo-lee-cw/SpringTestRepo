package com.example.demo.controller

import com.example.demo.dto.HelloRequestDto
import com.example.demo.dto.HelloResponseDto
import com.example.demo.service.HelloService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1")
class HelloTestApi(
    val helloService: HelloService
) {

    @GetMapping()
    fun helloWorld(): HelloResponseDto {
        return HelloResponseDto("success")
    }


    @GetMapping("/print-person")
    fun PrintPerson(@RequestBody request: HelloRequestDto): HelloResponseDto {
        return helloService.printPersonName(request)
    }

}