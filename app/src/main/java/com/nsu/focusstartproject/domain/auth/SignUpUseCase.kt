package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend fun invoke(userName: String, password: String): DataStatus<Any> =
        authRepository.signUp(userName = userName, password = password)
}