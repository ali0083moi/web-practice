package com.example.demo.exception

import com.example.demo.dto.ErrorResponse
import com.example.demo.model.FieldType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import jakarta.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(FormNotFoundException::class)
    fun handleFormNotFoundException(
        ex: FormNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = ex.message ?: "Form not found",
            path = request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(FormValidationException::class)
    fun handleFormValidationException(
        ex: FormValidationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = ex.message ?: "Invalid form data",
            path = request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message ?: "An unexpected error occurred",
            path = request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val message = when {
            ex.message?.contains("FieldType") == true -> {
                "Invalid field type. Allowed values are: ${FieldType.values().joinToString(", ")}"
            }
            else -> "Invalid request format"
        }
        
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = message,
            path = request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
} 