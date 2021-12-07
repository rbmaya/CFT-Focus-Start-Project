package com.nsu.focusstartproject.utils.errors_processing

import com.nsu.focusstartproject.data.loans_network.NoConnectivityException
import com.nsu.focusstartproject.data.loans_network.NoInternetException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toErrorCode(): ErrorCode {
    return when (this) {
        is SocketTimeoutException -> {
            ErrorCode.SOCKET_TIMEOUT
        }
        is UnknownHostException -> {
            ErrorCode.UNKNOWN_HOST
        }
        is NoConnectivityException -> {
            ErrorCode.NO_CONNECTIVITY
        }
        is NoInternetException -> {
            ErrorCode.NO_INTERNET
        }
        else -> ErrorCode.UNKNOWN
    }
}