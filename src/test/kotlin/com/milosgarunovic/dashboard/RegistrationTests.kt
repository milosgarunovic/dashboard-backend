package com.milosgarunovic.dashboard

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationTests : AbstractTestContainers() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `register user returns 201 created`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "email1@gmail.com", "password" to "password"))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)
    }

    @Test
    fun `register same email twice returns error`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "sameEmail@gmail.com", "password" to "password"))
        // first one passes since it doesn't exist
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)

        // second one with same content doesn't pass because it's already registered
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict)
    }
}