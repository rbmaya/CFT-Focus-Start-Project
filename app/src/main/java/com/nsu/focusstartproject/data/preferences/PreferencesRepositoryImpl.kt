package com.nsu.focusstartproject.data.preferences

import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
): PreferencesRepository {

    override suspend fun getToken(): String {
        return preferencesDataSource.getToken().first()
    }

    override suspend fun setToken(token: String) {
        return preferencesDataSource.setToken(token = token)
    }

    override suspend fun deleteToken() {
        return preferencesDataSource.deleteToken()
    }

    override suspend fun isFirstEnter(): Boolean {
        return preferencesDataSource.isFirstEnter().first()
    }

    override suspend fun setFirstEnter(isFirst: Boolean) {
        preferencesDataSource.setFirstEnter(isFirst = isFirst)
    }

    override suspend fun isDarkMode(): Boolean {
        return preferencesDataSource.isDarkMode().first()
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        preferencesDataSource.setDarkMode(isDarkMode = isDarkMode)
    }
}