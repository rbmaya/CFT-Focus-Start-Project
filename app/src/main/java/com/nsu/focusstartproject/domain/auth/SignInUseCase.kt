package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(userName: String, password: String): DataStatus<String> =
        authRepository.signIn(userName = userName, password = password)
}