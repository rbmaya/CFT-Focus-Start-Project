package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.domain.UserInfo
import retrofit2.Response

interface AuthDataSource {
    suspend fun signIn(userInfo: UserInfo): Response<String>

    suspend fun signUp(userInfo: UserInfo): Response<UserDto>
}