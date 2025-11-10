plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
val resilience4j = "2.2.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


allprojects {
    repositories {
        mavenCentral()
		maven("https://packages.confluent.io/maven/")
    }
}


subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

		// Logging
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

        // test
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

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
