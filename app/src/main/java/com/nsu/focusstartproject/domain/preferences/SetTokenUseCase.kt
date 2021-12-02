package com.nsu.focusstartproject.domain.preferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
){
    suspend fun invoke(token: String) =
        preferencesRepository.setToken(token = token)
}