package com.milosgarunovic.dashboard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TaskController {

    @GetMapping("/")
    fun home() = "home"

}