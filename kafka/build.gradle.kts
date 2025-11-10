plugins {
    // avro
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "common"
val avroVersion = "1.11.4"
val avroSerializer = "7.7.0"

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // kafka
	implementation("org.springframework.kafka:spring-kafka")

	// avro
	kapt("org.apache.avro:avro:${avroVersion}")
	implementation("org.apache.avro:avro:${avroVersion}")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro:2.14.2")

	kapt("io.confluent:kafka-avro-serializer:${avroSerializer}")
	implementation("io.confluent:kafka-avro-serializer:${avroSerializer}")
}

tasks.register("prepareKotlinBuildScriptModel") {}


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