package com.milosgarunovic.dashboard.spring.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ResourceAlreadyExistsException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String?) : RuntimeException(message)

// TODO remove trace from error