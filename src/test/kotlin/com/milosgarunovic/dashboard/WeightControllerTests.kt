package com.milosgarunovic.dashboard

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import com.milosgarunovic.dashboard.util.registerUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class WeightControllerTests : AbstractTestContainers() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var jwtSupport: JwtSupport


    @Test
    fun `POST weight and expect 1 element with correct weight`() {
        val accessToken = registerUserAndGetAccessToken("weights@test.com")

        // test response metadata
        val response = createWeight(60.0, accessToken)
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()

        // test response body
        val resAsString = response.response.contentAsString
        val res = objectMapper.readValue(resAsString, object : TypeReference<List<WeightResponse>>() {})
        assertEquals(1, res.size)
        assertEquals(60.0, res[0].value)
    }

    // create multiple weight data and make sure it has correct count
    @Test
    fun `POST multiple weights and expect 3 element with correct weight`() {
        val accessToken = registerUserAndGetAccessToken("weights2@test.com")

        createWeight(60.0, accessToken)
        createWeight(60.5, accessToken)
        val response = createWeight(60.6, accessToken).andReturn()
        // test response metadata

        // test response body
        val resAsString = response.response.contentAsString
        val res = objectMapper.readValue(resAsString, object : TypeReference<List<WeightResponse>>() {})
        assertEquals(3, res.size)
        // test response is in newest to oldest order
        assertEquals(60.6, res[0].value)
        assertEquals(60.5, res[1].value)
        assertEquals(60.0, res[2].value)
    }
    // test for validation on add and update

    // TODO use code coverage | add code coverage tool

    // update weight value, date and unit

    // delete and then get doesn't have that weight then

    // get returns the same as add, same count etc
    @Test
    fun `GET weight expect unauthorized`() {
        mockMvc.perform(get("/weight"))
            .andExpect(status().isUnauthorized)
    }

    fun registerUserAndGetAccessToken(email: String): String {
        mockMvc.registerUser(objectMapper, email, "Password123!")
        // instead of login, just generate the token (https://stackoverflow.com/a/45247733)
        return jwtSupport.generateAccessToken(email)
    }

    fun createWeight(value: Double, accessToken: String): ResultActions {
        val req = post("/weight")
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(WeightRequest(value)))
        return mockMvc.perform(req)
    }
}