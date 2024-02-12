package com.milosgarunovic.dashboard

import com.fasterxml.jackson.databind.ObjectMapper
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TaskTests : AbstractTestContainers() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtSupport: JwtSupport

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `task get expect unauthorized`() {
        mockMvc.perform(get("/task/"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `task get expect ok`() {
        // register user so it exists in database
        val email = "tasks@gmail.com"
        mockMvc.perform(
            post("/user/register")
                .content(objectMapper.writeValueAsBytes(mapOf("email" to email, "password" to "password")))
                .contentType(MediaType.APPLICATION_JSON)
        )

        // instead of login, just generate the token (https://stackoverflow.com/a/45247733)
        val accessToken = jwtSupport.generateAccessToken(email)
        mockMvc.perform(get("/task/").header("Authorization", "Bearer $accessToken"))
            .andExpect(status().isOk)
    }

}