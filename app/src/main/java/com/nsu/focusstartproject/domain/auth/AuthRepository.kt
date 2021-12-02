package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.utils.DataStatus

interface AuthRepository {
    suspend fun signIn(userName: String, password: String): DataStatus<String>

    suspend fun signUp(userName: String, password: String): DataStatus<Any>
}