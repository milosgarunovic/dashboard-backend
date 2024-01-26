import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22" // kotlin plugin for no args constructor for jpa

    // shadow jar for fat jar and simple execution
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.milosgarunovic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val springBootVersion = "3.2.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // database
    implementation("org.liquibase:liquibase-core:4.25.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")
    implementation("org.hibernate:hibernate-core:5.6.15.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
    implementation("io.hypersistence:hypersistence-tsid:2.1.0")
    implementation("io.hypersistence:hypersistence-utils-hibernate-55:3.2.0")
    implementation("net.ttddyy:datasource-proxy:1.10")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:postgresql:1.17.3")

    runtimeOnly("org.postgresql:postgresql:42.5.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict") // for jvm primitives we have to put @field:NotNull
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.milosgarunovic.dashboard.App"))
        }
    }
}