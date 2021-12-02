package com.nsu.focusstartproject.data

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
): AuthDataSource{

    override suspend fun signIn(userName: String, password: String): Response<String> {
        return authApi.signIn(userName = userName, password = password)
    }

    override suspend fun signUp(userName: String, password: String): Response<UserDto> {
        return authApi.signUp(userName = userName, password = password)
    }
}