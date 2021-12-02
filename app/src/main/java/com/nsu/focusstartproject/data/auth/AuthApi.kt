package com.nsu.focusstartproject.data.auth

import retrofit2.Response
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun signIn(userName: String, password: String): Response<String>

    @POST("/registration")
    suspend fun signUp(userName: String, password: String): Response<UserDto>
}