package com.nsu.focusstartproject.domain.preferences

interface PreferencesRepository {
    suspend fun getToken(): String

    suspend fun setToken(token: String)

    suspend fun deleteToken()

    suspend fun isFirstEnter(): Boolean

    suspend fun setFirstEnter(isFirst: Boolean)
}