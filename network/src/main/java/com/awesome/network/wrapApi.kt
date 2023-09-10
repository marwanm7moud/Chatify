package com.awesome.network

import com.awesome.entities.utils.HttpStatusCode
import com.awesome.entities.utils.NetworkException
import com.awesome.entities.utils.NullDataException
import com.awesome.entities.utils.ServerException
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.entities.utils.ValidationException
import com.quickblox.core.exception.QBResponseException
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException

suspend fun <T> wrapApi(call: suspend () -> T): T {
    return try {
        call() ?: throw NullDataException("Null")
    } catch (e: QBResponseException) {
        when (e.httpStatusCode) {
            HttpStatusCode.NoInternet.code -> throw NetworkException(e.errors.toString())
            HttpStatusCode.BadRequest.code -> throw NetworkException(e.errors.toString())
            HttpStatusCode.Unauthorized.code -> throw UnauthorizedException(e.errors.toString())
            HttpStatusCode.Forbidden.code -> throw NetworkException(e.errors.toString())
            HttpStatusCode.NotFound.code -> throw NetworkException(e.errors.toString())
            HttpStatusCode.Validation.code -> throw ValidationException(e.errors)
            HttpStatusCode.TooManyRequests.code -> throw NetworkException(e.errors.toString())
            HttpStatusCode.InternalServerError.code -> throw ServerException(e.errors.toString())
            HttpStatusCode.ServiceUnavailable.code -> throw ServerException(e.errors.toString())
            HttpStatusCode.DeletedUser.code -> throw UpdatedOrDeletedUserException(e.errors.toString())
        }
        throw Exception(e.message)
    } catch (e: Throwable) {
        throw Exception(e.message)
    } catch (e: SmackException.NotConnectedException) {
        throw NetworkException(e.message)
    } catch (e: SmackException.NotLoggedInException) {
        throw UnauthorizedException(e.message)
    } catch (e: SmackException.NoResponseException) {
        throw Exception(e.message)
    } catch (e: XMPPException) {
        throw Exception(e.message)
    }
}
