package com.app.testexample.utils

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkUtils {
    companion object {
        fun resolveError(e: Exception): State.ErrorState {
            var error = e
            when (e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException(errorMessage = "connection error!")
                }
                is ConnectException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }
                is UnknownHostException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }
                is HttpException -> {
                    when (e.code()) {
                        502 -> {
                            error = NetworkErrorException("internal error!")
                        }
                        401 -> {
                            throw AuthenticationException(
                                e.response()?.errorBody()?.string() ?: "authentication error!"
                            )
                        }
                        400 -> {
                            error = NetworkErrorException.parseException(e)
                        }
                    }
                }
                else -> error = NetworkErrorException(errorMessage = e.toString())
            }
            return State.ErrorState(error)
        }
    }
}
