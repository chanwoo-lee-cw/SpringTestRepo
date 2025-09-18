package com.example.demo.service

import com.example.demo.aop.annotation.MeasureRuntime
import com.example.demo.dto.HelloRequestDto
import com.example.demo.dto.HelloResponseDto
import com.example.demo.dto.PrintHelloDto
import com.example.demo.producer.PrintHelloProducer
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class HelloService(
    val printHelloProducer: PrintHelloProducer,
) {

    val log = KotlinLogging.logger { }


    /**
     * Cacheable
     * 메소드 실행 후에 캐시를 저장한다. 만약 이미 캐싱된 값이 있으면 메소드를 실행하지 않고 캐싱된 값을 반환
     * - value: 캐시 이름 (여러 개 가능)
     * - key: 캐시에 저장할 키
     * - unless: 조건에 따라 캐싱하지 않는다.
     * - condition: 조건이 일치하면 캐싱한다
     */
    @Cacheable(
        value = ["personNameCache"],
        key = "#helloRequestDto.name + ':' + #helloRequestDto.age"
    )
    fun printPersonName(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        return HelloResponseDto("success")
    }

    @Cacheable(
        value = ["personNameCache"],
        key = "#helloRequestDto.name + ':' + #helloRequestDto.age",
        condition = "#helloRequestDto.name.length() > 5"
    )
    fun printPersonNameCondition(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        return HelloResponseDto("success")
    }

    /**
     * CachePut
     * 메소드 실행 후에 항상 캐시를 저장한다. 캐시를 읽지 않는다.
     */
    @CachePut(
        value = ["personNameCache"],
        key = "#helloRequestDto.name + ':' + #helloRequestDto.age",
    )
    fun printPersonNamePut(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        return HelloResponseDto("success")
    }

    /**
     * CacheEvict
     * 메소드 처리 후에 캐싱을 삭제한다
     * - allEntries: true로 설정하면 캐시의 모든 Entries 삭제
     * - beforeInvocation: 기본은 false, 메서드 실행 성공 후 삭제. true, 메서드 실행 전에 삭제
     */
    @CacheEvict(
        value = ["personNameCache"],
        key = "#helloRequestDto.name + ':' + #helloRequestDto.age",
    )
    fun printPersonNameEvictByKey(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        return HelloResponseDto("success")
    }

    @CacheEvict(
        value = ["personNameCache"],
        allEntries = true
    )
    fun printPersonNameEvictAll(
        helloRequestDto: HelloRequestDto
    ): HelloResponseDto {
        return HelloResponseDto("success")
    }


    fun printHelloKafka() {
        printHelloProducer.sendHelloPrint(
            PrintHelloDto(
                "Hello World"
            )
        )
    }
}