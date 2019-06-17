package mybooks.exceptions

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(OpenLibraryServerException::class)
    fun handleOpenLibraryServerException(ex: Exception): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.SERVICE_UNAVAILABLE
        val errorResponse = ErrorResponse(Date(), status.value(), ex.javaClass.canonicalName, status, ex.message)
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(OpenLibraryClientException::class)
    fun handleOpenLibraryClientException(ex: Exception): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(Date(), status.value(), ex.javaClass.canonicalName, status, ex.message)
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(OpenLibraryNotFoundException::class)
    fun handleOpenLibraryNotFoundException(ex: Exception): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND
        val errorResponse = ErrorResponse(Date(), status.value(), ex.javaClass.canonicalName, status, ex.message)
        return ResponseEntity(errorResponse, status)
    }
}