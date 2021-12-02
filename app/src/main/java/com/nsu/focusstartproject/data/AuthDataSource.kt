package com.nsu.focusstartproject.data

import retrofit2.Response

interface AuthDataSource {
    suspend fun signIn(userName: String, password: String): Response<String>

    suspend fun signUp(userName: String, password: String): Response<UserDto>
}