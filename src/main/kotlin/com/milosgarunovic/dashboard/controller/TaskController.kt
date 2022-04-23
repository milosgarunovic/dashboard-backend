package com.milosgarunovic.dashboard.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController {

    @GetMapping("/")
    fun home() = "home"

    @PostMapping("/")
    fun addTask() {

    }
}