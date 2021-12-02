package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.domain.UserInfo
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(userInfo: UserInfo): DataStatus<Any> =
        authRepository.signUp(userInfo = userInfo)
}