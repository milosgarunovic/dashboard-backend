package com.milosgarunovic.dashboard.controller

import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(TaskController::class)
class TaskControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testTaskController() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("home"))
            .andExpect(content().string(containsString("Welcome to my dashboard")))
    }
}