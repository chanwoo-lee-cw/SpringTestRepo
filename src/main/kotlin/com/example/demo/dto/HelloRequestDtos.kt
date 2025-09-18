package com.example.demo.dto

import java.io.Serializable

data class HelloRequestDto(
    val name: String,
    val age: Int
)

data class HelloResponseDto(
    val success: String
) : Serializable