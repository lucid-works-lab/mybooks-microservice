package mybooks.exceptions

import org.springframework.http.HttpStatus
import java.util.Date

data class ErrorResponse(val timestamp: Date, val status: Int, val error: String, val httpStatus: HttpStatus, val message: String?)
