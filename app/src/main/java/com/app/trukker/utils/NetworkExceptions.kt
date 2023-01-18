package com.app.trukker.utils

import org.json.JSONObject
import retrofit2.HttpException

open class NetworkErrorException(val errorMessage: String) : Exception() {

    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage(): String {
        return errorMessage
    }

    companion object {
        fun parseException(e: HttpException): NetworkErrorException {
            val errorBody = e.response()?.errorBody()?.string()

            return try {
                NetworkErrorException(JSONObject(errorBody!!).getString("message"))
            } catch (_: Exception) {
                NetworkErrorException("unexpected error!!")
            }
        }
    }
}

class AuthenticationException(authMessage: String) : NetworkErrorException(errorMessage = authMessage)
