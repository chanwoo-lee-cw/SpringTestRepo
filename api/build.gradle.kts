plugins {
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "common"

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":kafka"))

    implementation("org.springframework.boot:spring-boot-starter-web")


	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}

tasks.register("prepareKotlinBuildScriptModel") {}