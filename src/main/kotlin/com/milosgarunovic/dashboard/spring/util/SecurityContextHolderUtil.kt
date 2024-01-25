package com.milosgarunovic.dashboard.spring.util

import com.milosgarunovic.dashboard.spring.UsernamePasswordAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class SecurityContextHolderUtil {
    companion object {
        fun getAuth(): UsernamePasswordAuthentication {
            return SecurityContextHolder.getContext().authentication as UsernamePasswordAuthentication
        }

        fun getUserId(): UUID {
            return getAuth().id
        }
    }
}
