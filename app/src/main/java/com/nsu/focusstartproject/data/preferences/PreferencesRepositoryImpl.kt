package com.nsu.focusstartproject.data.preferences

import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow
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
}