package com.nsu.focusstartproject.domain.preferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDarkModeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(isDarkMode: Boolean) {
        return preferencesRepository.setDarkMode(isDarkMode = isDarkMode)
    }
}