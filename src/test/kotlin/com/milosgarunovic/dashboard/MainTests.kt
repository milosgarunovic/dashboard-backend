package com.milosgarunovic.dashboard

import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtSupport: JwtSupport

    @Test
    fun `task get expect unauthorized`() {
        mockMvc.perform(get("/task/"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `task get expect ok`() {
        // TODO this user needs to exist in database, see how to change that
        // https://stackoverflow.com/a/45247733
        val accessToken = jwtSupport.generateAccessToken("test@gmail.com")
        mockMvc.perform(get("/task/").header("Authorization", "Bearer $accessToken"))
            .andExpect(status().isOk)
    }

}