package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.domain.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun signIn(@Body userInfo: UserInfo): Response<String>

    @POST("/registration")
    suspend fun signUp(@Body userInfo: UserInfo): Response<UserDto>
}