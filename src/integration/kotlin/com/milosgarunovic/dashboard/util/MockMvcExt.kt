package com.milosgarunovic.dashboard.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.milosgarunovic.dashboard.domain.`typealias`.Email
import com.milosgarunovic.dashboard.domain.`typealias`.Password
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

fun MockMvc.registerUser(objectMapper: ObjectMapper, email: Email, password: Password) {
    perform(
        post("/user/register")
            .content(objectMapper.writeValueAsBytes(mapOf("email" to email, "password" to password)))
            .contentType(MediaType.APPLICATION_JSON)
    )
}