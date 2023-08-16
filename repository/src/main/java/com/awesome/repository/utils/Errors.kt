package com.awesome.repository.utils

open class ChatifyException(message : String?) : Throwable(message)

open class ValidationException(message: String?) : ChatifyException(message)
open class NullDataException(message: String?) : ChatifyException(message)
open class NetworkException(message: String?) : ChatifyException(message)
open class ServerException(message: String?) : ChatifyException(message)
open class UnauthorizedException(message: String?) : ChatifyException(message)




