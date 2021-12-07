package com.nsu.focusstartproject.utils.errors_processing

import android.content.Context
import com.nsu.focusstartproject.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorCodeProcessor @Inject constructor(
    @ApplicationContext context: Context
) {
    private val resources = context.resources

    fun processErrorCode(errorCode: ErrorCode): String {
        return when (errorCode) {
            ErrorCode.UNAUTHORIZED -> {
                resources.getString(R.string.unauthorized_error_body)
            }
            ErrorCode.FORBIDDEN -> {
                resources.getString(R.string.forbidden_error_body)
            }
            ErrorCode.NOT_FOUND -> {
                resources.getString(R.string.not_found_error_body)
            }
            ErrorCode.UNKNOWN -> {
                resources.getString(R.string.unknown_error_body)
            }
            ErrorCode.NO_INTERNET -> {
                resources.getString(R.string.no_internet_available)
            }
            ErrorCode.UNKNOWN_HOST -> {
                resources.getString(R.string.unknown_host)
            }
            ErrorCode.NO_CONNECTIVITY -> {
                resources.getString(R.string.no_network_available)
            }
            ErrorCode.SOCKET_TIMEOUT -> {
                resources.getString(R.string.socket_timeout)
            }
        }
    }
}