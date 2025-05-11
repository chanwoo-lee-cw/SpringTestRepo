package com.example.demo.dto

data class HelloRequestDto(
    val name: String,
    val age: Int
)

data class HelloResponseDto(
    val success: String
)