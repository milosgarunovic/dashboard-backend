package com.milosgarunovic.dashboard.spring.exception

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleResourceAlreadyExists(e: ResourceAlreadyExistsException): ResponseEntity<Any> {
        val body = mapOf("errorMessage" to e.message)
        return ResponseEntity<Any>(body, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ResponseEntity<Any> {
        val body = mapOf("errorMessage" to e.message)
        return ResponseEntity<Any>(body, HttpStatus.BAD_REQUEST)
    }
}