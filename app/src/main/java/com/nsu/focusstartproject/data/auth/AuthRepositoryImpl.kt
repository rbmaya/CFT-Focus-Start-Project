package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.domain.UserInfo
import com.nsu.focusstartproject.domain.auth.AuthRepository
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.toDataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository {

    override suspend fun signIn(userInfo: UserInfo): DataStatus<String> {
        return withContext(Dispatchers.IO){
            val response = authDataSource.signIn(userInfo = userInfo)
            response.toDataStatus()
        }
    }

    override suspend fun signUp(userInfo: UserInfo): DataStatus<Any> {
        return withContext(Dispatchers.IO) {
            val response = authDataSource.signUp(userInfo = userInfo)
            response.toDataStatus()
        }
    }

}