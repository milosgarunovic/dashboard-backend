package com.milosgarunovic.dashboard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.web.FilterChainProxy
import org.springframework.stereotype.Component
import javax.servlet.Filter


@SpringBootApplication
class DashboardBackendApplication

fun main(args: Array<String>) {
    runApplication<DashboardBackendApplication>(*args)
}

@Component
class SecurityFilterChainPrinter : CommandLineRunner {

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private lateinit var springSecurityFilterChain: Filter

    override fun run(vararg args: String?) {
        val filterChainProxy = springSecurityFilterChain as FilterChainProxy
        filterChainProxy.filterChains
            .flatMap { it.filters }
            .forEachIndexed { i, filter -> println("$i ${filter.javaClass}") }
    }
}