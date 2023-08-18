package com.awesome.entities.utils

open class ChatifyException(message : String?) : Throwable(message)

open class NullDataException(message: String?) : ChatifyException(message)
open class NetworkException(message: String?) : ChatifyException(message)
open class ServerException(message: String?) : ChatifyException(message)
open class UnauthorizedException(message: String?) : ChatifyException(message)


open class ValidationException(val messages: List<String>?) : ChatifyException(messages.toString())
open class PasswordValidationException(message: String) : ValidationException(null)
open class EmailValidationException(message: String) : ValidationException(null)
open class FullNameValidationException(message: String) : ValidationException(null)
open class UsernameValidationException(message: String) : ValidationException(null)





