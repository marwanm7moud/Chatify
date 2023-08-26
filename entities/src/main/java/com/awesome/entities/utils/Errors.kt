package com.awesome.entities.utils

open class ChatifyException(message: String?) : Throwable(message)

class NullDataException(message: String?) : ChatifyException(message)
class NetworkException(message: String?) : ChatifyException(message)
class ServerException(message: String?) : ChatifyException(message)



open class ValidationException(val messages: List<String>?) : ChatifyException(messages.toString())
class UnauthorizedException(message: String?) : ValidationException(listOf(message.orEmpty()))
class UpdatedOrDeletedUserException(message: String?) : ValidationException(listOf(message.orEmpty()))




