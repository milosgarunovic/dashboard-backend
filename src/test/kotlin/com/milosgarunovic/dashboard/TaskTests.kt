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
        // must register user first
        val jsonContent = objectMapper.writeValueAsBytes(mapOf("email" to "tasks@gmail.com", "password" to "password"))
        // register user so it exists in database
        mockMvc.perform(
            post("/user/register")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
        )

        // instead of login, just generate the token (https://stackoverflow.com/a/45247733)
        val accessToken = jwtSupport.generateAccessToken("tasks@gmail.com")
        mockMvc.perform(get("/task/").header("Authorization", "Bearer $accessToken"))
            .andExpect(status().isOk)
    }

}