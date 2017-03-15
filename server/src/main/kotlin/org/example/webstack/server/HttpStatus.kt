package org.example.webstack.server

/**
 * General HTTP status codes.
 * @author Nick Apperley
 */
enum class HttpStatus(val num: Int) {
    OK(200), NOT_FOUND(404), SERVER_ERROR(500)
}