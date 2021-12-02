package com.nsu.focusstartproject.domain.preferences

interface PreferencesRepository {
    suspend fun getToken(): String

    suspend fun setToken(token: String)

    suspend fun deleteToken()
}