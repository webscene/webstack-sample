package org.example.webstack.client

/**
 * General HTTP status codes.
 * @author Nick Apperley
 */
enum class HttpStatus(val num: Short) {
    OK(200),
    NOT_FOUND(404),
    SERVER_ERROR(500)
}