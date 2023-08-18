package com.awesome.entities.utils

enum class HttpStatusCode(val code : Int) {
    NoInternet(0),
    BadRequest(400),
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    Validation(422),
    TooManyRequests(429),
    InternalServerError(500),
    ServiceUnavailable(503),
}