package org.example.webstack.server

/**
 * General content types used in a HTTP/S request or response.
 * @author Nick Apperley
 */
enum class ContentType(val txt: String) {
    HTML("text/html"),
    JSON("application/json"),
    PLAIN_TXT("text/plain")
}