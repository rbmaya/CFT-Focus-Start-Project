package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.domain.UserInfo
import com.nsu.focusstartproject.utils.DataStatus

interface AuthRepository {
    suspend fun signIn(userInfo: UserInfo): DataStatus<String>

    suspend fun signUp(userInfo: UserInfo): DataStatus<Any>
}