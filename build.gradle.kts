plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

val springBootVersion: String by project
val kotlinVersion: String by project
val mysqlVersion: String by project
val jacksonVersion: String by project
val jwtVersion: String by project
val h2Version: String by project

group = "com.eco-picker"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
        exclude(group = "commons-logging", module = "commons-logging")
    }
}

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-mustache:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-mail:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
    // logging
    implementation("io.github.oshai:kotlin-logging:6.0.3")
    // open API
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.3")
    // database
    runtimeOnly("com.h2database:h2:$h2Version")
    runtimeOnly("mysql:mysql-connector-java:$mysqlVersion")
    // dev
    developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    doFirst {
        val envVars = System.getenv()
        for ((key, value) in envVars) {
            println("$key: $value")
        }
    }
}
