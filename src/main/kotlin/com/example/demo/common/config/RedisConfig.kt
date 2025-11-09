package com.example.demo.common.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration
import java.util.*


@Configuration
@EnableCaching
@ConditionalOnProperty(value = ["redis.enabled"], havingValue = "true", matchIfMissing = true)
class RedisConfig(
    private val redisProperties: RedisProperties,
) {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val config = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisCacheConfiguration() = RedisCacheConfiguration
        .defaultCacheConfig()
        .prefixCacheNameWith("hello::")
        .entryTtl(Duration.ofMinutes(5))


    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        val redisCacheConfiguration = redisCacheConfiguration()


        val cacheManager = RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(
                redisCacheConfiguration
            )
            .transactionAware()
            .withInitialCacheConfigurations(
                // 테스트용 설정
                /**
                Collections.singletonMap(
                    "predefined", RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                )
                **/
                 redisCacheConfigMap()
            )
            .build()

        return cacheManager
    }


    private fun redisCacheConfigMap(): Map<String, RedisCacheConfiguration> {
        return mapOf(
            "productCache" to RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(5))
                .prefixCacheNameWith("product::")
                .disableCachingNullValues(),
            "orderCache" to RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .prefixCacheNameWith("order::")
                .disableCachingNullValues(),
            "categoriesCache" to RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(10))
                .prefixCacheNameWith("categories::"),
            "personNameCache" to RedisCacheConfiguration.defaultCacheConfig(),
        )
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()

        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()

        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()
        return redisTemplate
    }


    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .setAddress("redis://${redisProperties.host}:${redisProperties.port}")
            .setConnectionMinimumIdleSize(10)
        return Redisson.create(config)
    }

}