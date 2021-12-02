package com.nsu.focusstartproject.domain.preferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
){
    suspend fun invoke() = preferencesRepository.deleteToken()
}