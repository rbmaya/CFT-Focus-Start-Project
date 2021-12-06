package com.nsu.focusstartproject.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    suspend fun getToken(): Flow<String>

    suspend fun setToken(token: String)

    suspend fun deleteToken()

    suspend fun isFirstEnter(): Flow<Boolean>

    suspend fun setFirstEnter(isFirst: Boolean)
}