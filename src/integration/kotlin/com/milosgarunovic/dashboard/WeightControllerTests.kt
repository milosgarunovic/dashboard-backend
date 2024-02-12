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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime

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

    @Test
    fun `POST multiple weights and expect 3 element with correct weight`() {
        val accessToken = registerUserAndGetAccessToken("weights2@test.com")

        createWeight(60.0, accessToken)
        createWeight(60.5, accessToken)
        val response = createWeight(60.6, accessToken).andReturn()

        val res =
            objectMapper.readValue(response.response.contentAsString, object : TypeReference<List<WeightResponse>>() {})
        assertEquals(3, res.size)
        // test response is in newest to oldest order
        assertEquals(60.6, res[0].value)
        assertEquals(60.5, res[1].value)
        assertEquals(60.0, res[2].value)
    }

    // test for validation on add and update
    @Test
    fun `POST validate validations`() {
        val accessToken = registerUserAndGetAccessToken("weights3@test.com")

        // first case negative value
        val validation1 = createWeight(-10.0, accessToken)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val error1 = deserialize<Map<String, String>>(validation1)["errorMessage"]
        assertEquals("value can't be negative", error1)

        // second case value over 200 kg
        val validation2 = createWeight(201.0, accessToken)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val error2 = deserialize<Map<String, String>>(validation2)["errorMessage"]
        assertEquals("maximum value is 200", error2)

        // third case value can't be null
        val validation3 = createWeight(null, accessToken)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val error3 = deserialize<Map<String, String>>(validation3)["errorMessage"]
        assertEquals("value can't be null", error3)

        // fourth case date can't be in future
        val validation4 = createWeight(70.0, accessToken, OffsetDateTime.now().plusDays(1))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val error4 = deserialize<Map<String, String>>(validation4)["errorMessage"]
        assertEquals("date can't be in future", error4)
    }

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

    fun createWeight(value: Double?, accessToken: String, date: OffsetDateTime? = null): ResultActions {
        val req = post("/weight")
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(WeightRequest(value, date)))
        return mockMvc.perform(req)
    }

    // TODO doesn't work as intended with for example List<WeightResponse>
    fun <T> deserialize(result: MvcResult): T {
        return objectMapper.readValue(result.response.contentAsString, object : TypeReference<T>() {})
    }
}