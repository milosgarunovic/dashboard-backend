package com.milosgarunovic.dashboard.spring.config

import com.milosgarunovic.dashboard.spring.filter.CustomLoggingReqResFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingConfig {

    @Bean
    fun customLoggingReqResFilter() = CustomLoggingReqResFilter()

}