package com.milosgarunovic.dashboard

import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DashboardBackendApplication

fun main(args: Array<String>) {
    runApplication<DashboardBackendApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}