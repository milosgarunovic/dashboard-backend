package com.milosgarunovic.dashboard.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ResourceAlreadyExistsException(message: String?) : RuntimeException(message) {
}