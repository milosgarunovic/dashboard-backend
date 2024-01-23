package com.milosgarunovic.dashboard.spring.util

import com.milosgarunovic.dashboard.spring.UsernamePasswordAuthentication
import org.springframework.security.core.context.SecurityContextHolder

class SecurityContextHolderUtil {
    companion object {
        fun getAuth(): UsernamePasswordAuthentication {
            return SecurityContextHolder.getContext().authentication as UsernamePasswordAuthentication
        }
    }
}
