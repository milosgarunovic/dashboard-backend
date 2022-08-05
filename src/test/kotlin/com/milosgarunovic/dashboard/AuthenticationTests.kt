package com.milosgarunovic.dashboard

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:14.2-alpine3.15")

        @JvmStatic
        @DynamicPropertySource
        fun dynamicPropertySource(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }

    @Test
    fun `login with correct user and pass returns 200 and response`() {
        val jsonContent = objectMapper.writeValueAsBytes(mapOf("email" to "test@gmail.com", "password" to "password"))
        // create user
        mockMvc.perform(post("/user/register")
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)

        // login with created user
        mockMvc.perform(
            post("/login").content(jsonContent)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        // TODO check if both accessToken and refreshToken exist
    }

    @Test
    fun `login with correct user and wrong pass returns 401 unauthorized`() {
        mockMvc.perform(
            post("/login")
                .content(
                    objectMapper.writeValueAsBytes(
                        mapOf("email" to "test@gmail.com", "password" to "wrongPassword")
                    )
                )
        )
            .andExpect(status().isUnauthorized)
    }
}