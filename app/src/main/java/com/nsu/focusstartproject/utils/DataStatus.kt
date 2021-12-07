package com.nsu.focusstartproject.utils

import com.nsu.focusstartproject.utils.errors_processing.ErrorCode
import retrofit2.Response

sealed class DataStatus<out R> {
    object Loading: DataStatus<Nothing>()
    data class Success<out T>(val data: T? = null) : DataStatus<T>()
    data class Error(val code: ErrorCode? = null) : DataStatus<Nothing>()
}

fun <T> Response<T>.toDataStatus(): DataStatus<T>{
    if (this.isSuccessful){
        return DataStatus.Success(this.body()!!)
    }
    return when (this.code()){
        401 -> DataStatus.Error(code = ErrorCode.UNAUTHORIZED)
        403 -> DataStatus.Error(code = ErrorCode.FORBIDDEN)
        404 -> DataStatus.Error(code = ErrorCode.NOT_FOUND)
        else -> DataStatus.Error(code = ErrorCode.UNKNOWN)
    }
}