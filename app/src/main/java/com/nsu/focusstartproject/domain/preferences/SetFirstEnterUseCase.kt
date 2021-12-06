package com.nsu.focusstartproject.domain.preferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetFirstEnterUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(isFirst: Boolean) {
        return preferencesRepository.setFirstEnter(isFirst)
    }
}