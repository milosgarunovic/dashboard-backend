package com.milosgarunovic.dashboard

import org.springframework.test.context.DynamicPropertySource

abstract class AbstractTestContainers {

    init {
        TestContainers.instance
    }

    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun dynamicPropertySource(registry: org.springframework.test.context.DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", TestContainers.instance::getJdbcUrl)
            registry.add("spring.datasource.username", TestContainers.instance::getUsername)
            registry.add("spring.datasource.password", TestContainers.instance::getPassword)
        }
    }

}