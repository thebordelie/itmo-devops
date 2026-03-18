package org.example

import org.example.auth.InvalidCredentialsException
import org.example.task.TaskNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String?
)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentials(
        ex: InvalidCredentialsException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.UNAUTHORIZED
        return buildErrorResponse(status, ex.message, exchange)
    }

    @ExceptionHandler(TaskNotFoundException::class)
    fun handleTaskNotFound(
        ex: TaskNotFoundException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND
        return buildErrorResponse(status, ex.message, exchange)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(
        ex: DataIntegrityViolationException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.CONFLICT
        val message = "Data integrity violation"
        return buildErrorResponse(status, message, exchange)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(
        ex: Exception,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return buildErrorResponse(status, ex.message, exchange)
    }

    private fun buildErrorResponse(
        status: HttpStatus,
        message: String?,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            path = exchange.request.path.value()
        )
        return ResponseEntity.status(status).body(response)
    }
}

