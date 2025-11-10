plugins {
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "common"
val resilience4j = "2.2.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// Redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.redisson:redisson-spring-boot-starter:3.41.0")
	implementation("io.lettuce:lettuce-core:6.7.1.RELEASE")

	// resilience4j
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:${resilience4j}")
	implementation("io.github.resilience4j:resilience4j-annotations:${resilience4j}")
	implementation("io.github.resilience4j:resilience4j-spring-boot3:${resilience4j}")

}

tasks.register("prepareKotlinBuildScriptModel") {}