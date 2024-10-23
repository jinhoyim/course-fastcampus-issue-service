plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.6"
}

allprojects {
    group = "com.fastcampus.kotlinspring"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

}

val versions = mapOf(
    "java-jwt" to "4.4.0",
    "kotlin-logging-jvm" to "5.1.4"
)

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("io.spring.dependency-management")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
    }

    dependencies {
        implementation("com.auth0:java-jwt:${versions["java-jwt"]}")
        implementation("io.github.oshai:kotlin-logging-jvm:${versions["kotlin-logging-jvm"]}")

        implementation("org.jetbrains.kotlin:kotlin-reflect")

        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.register("prepareKotlinBuildScriptModel")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

