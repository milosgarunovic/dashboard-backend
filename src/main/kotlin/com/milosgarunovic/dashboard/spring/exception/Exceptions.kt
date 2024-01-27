package com.milosgarunovic.dashboard.spring.exception

class ResourceAlreadyExistsException(message: String?) : RuntimeException(message)

class BadRequestException(message: String?) : RuntimeException(message)