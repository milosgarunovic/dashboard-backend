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
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}

@Component
class SecurityFilterChainPrinter : CommandLineRunner {

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private lateinit var springSecurityFilterChain: Filter

    /**
     * Runs all the filters in order, so we can check what exists and where to put a filter, before/after which one.
     * This is here mostly for debugging, so maybe I should make it as a flag.
     */
    override fun run(vararg args: String?) {
        val filterChainProxy = springSecurityFilterChain as FilterChainProxy
        filterChainProxy.filterChains
            .flatMap { it.filters }
            .forEachIndexed { i, filter -> println("$i ${filter.javaClass}") }
    }
}