package com.milosgarunovic.dashboard

import com.fasterxml.jackson.databind.ObjectMapper
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
    fun `register user returns 201 Created`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "email1@gmail.com", "password" to "password"))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)
    }

    @Test
    fun `register same email twice returns 209 Conflict`() {
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

    @Test
    fun `register with no body returns 400 Bad Request`() {
        mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest)
    }

    @Test
    fun `register with no body and no Content-Type header returns 415 Unsupported Media Type`() {
        mockMvc.perform(post("/user/register")).andExpect(status().isUnsupportedMediaType)
    }

    @Test
    fun `register with wrong email format returns 400 Bad Request`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "notEmail", "password" to "password"))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
        // TODO expect to have a message in body explaining why it's a bad request
    }

    @Test
    fun `register with empty fields returns 400 Bad Request`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to null, "password" to null))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
        // TODO expect to have a message in body explaining why it's a bad request
    }

    @Test
    fun `register with empty email returns 400 Bad Request`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to null, "password" to "password"))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
        // TODO expect to have a message in body explaining why it's a bad request
    }

    @Test
    fun `register with empty password returns 400 Bad Request`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "emptyPassword@gmail.com", "password" to null))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
        // TODO expect to have a message in body explaining why it's a bad request
    }

    @Test
    fun `register with password with less than 8 chars returns 400 Bad Request`() {
        val content = objectMapper.writeValueAsBytes(mapOf("email" to "goodEmail@gmail.com", "password" to "pass"))
        mockMvc.perform(
            post("/user/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
        // TODO expect to have a message in body explaining why it's a bad request
    }
}