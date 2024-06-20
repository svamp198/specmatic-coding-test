package com.store.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): ResponseEntity<HashMap<String, Any?>> {
        return ResponseEntity(hashMapOf(
            "status" to 400,
            "error" to ex.message,
            "path" to ex.path,
            "timestamp" to LocalDateTime.now()
        ), HttpStatus.BAD_REQUEST)
    }

}
