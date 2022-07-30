package com.milosgarunovic.dashboard

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `login with correct user and pass returns 200 and response`() {
        mockMvc.perform(
            post("/login")
                .content(
                    objectMapper.writeValueAsBytes(
                        mapOf("email" to "test@gmail.com", "password" to "password"))))
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        // TODO check if both accessToken and refreshToken exist
    }

    @Test
    fun `login with correct user and pass returns 401 unauthorized`() {
        mockMvc.perform(
            post("/login")
                .content(
                    objectMapper.writeValueAsBytes(
                        mapOf("email" to "test@gmail.com", "password" to "wrongPassword"))))
            .andExpect(status().isUnauthorized)
    }
}