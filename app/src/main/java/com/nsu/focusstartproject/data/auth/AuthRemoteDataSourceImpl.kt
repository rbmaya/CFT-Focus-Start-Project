package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.domain.UserInfo
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
): AuthDataSource {

    override suspend fun signIn(userInfo: UserInfo): Response<String> {
        return authApi.signIn(userInfo = userInfo)
    }

    override suspend fun signUp(userInfo: UserInfo): Response<UserDto> {
        return authApi.signUp(userInfo = userInfo)
    }
}