plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("kapt") version "1.9.25"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	// avro
	id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
val avroVersion = "1.11.4"
val avroSerializer = "7.7.0"
val resilience4j = "2.2.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	maven("https://packages.confluent.io/maven/")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// resilience4j
	// 1. CircuitBreaker : 기본 기능
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:${resilience4j}")
	// 2. @CircuitBreaker, @Retry 등 애노테이션 제공 (메서드 단위 적용 시 필요)
	implementation("io.github.resilience4j:resilience4j-annotations:${resilience4j}")
	// 3) Spring Boot 3 : 자동 구성(AutoConfig) + AOP 어드바이스 연동 + 프로퍼티 바인딩 + application.yml 설정
	implementation("io.github.resilience4j:resilience4j-spring-boot3:${resilience4j}")
	// 4. Retry : 요청 실패 시 재시도 처리 기능
	implementation("io.github.resilience4j:resilience4j-retry:${resilience4j}")
	// 5. RateLimiter : 제한치를 넘어서 요청을 거부하거나 Queue 생성하여 처리하는 기능
	implementation("io.github.resilience4j:resilience4j-ratelimiter:${resilience4j}")
	// 6. TimeLimiter : 실행 시간제한 설정 기능
	implementation("io.github.resilience4j:resilience4j-timelimiter:${resilience4j}")
	// 7. Bulkhead : 동시 실행 횟수 제한 기능
	implementation("io.github.resilience4j:resilience4j-bulkhead:${resilience4j}")
	// 8. Cache : 결과 캐싱 기능
	implementation("io.github.resilience4j:resilience4j-cache:${resilience4j}")

	// Redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.redisson:redisson-spring-boot-starter:3.41.0")
	implementation("io.lettuce:lettuce-core:6.7.1.RELEASE")

	// Logging
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	// kafka
	implementation("org.springframework.kafka:spring-kafka")

	// avro
	kapt("org.apache.avro:avro:${avroVersion}")
	implementation("org.apache.avro:avro:${avroVersion}")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro:2.14.2")

	kapt("io.confluent:kafka-avro-serializer:${avroSerializer}")
	implementation("io.confluent:kafka-avro-serializer:${avroSerializer}")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
	testImplementation("io.kotest:kotest-property-jvm:5.9.1")
	testImplementation("io.mockk:mockk:1.13.17")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

avro {
	stringType.set("String")
	fieldVisibility.set("PRIVATE")
}

sourceSets {
	main {
		java {
			srcDirs("build/generated-main-avro-java")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
