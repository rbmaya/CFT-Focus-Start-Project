package com.nsu.focusstartproject.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) :
    PreferencesDataSource {

    companion object{
        val TOKEN = stringPreferencesKey("TOKEN")
        val FIRST_ENTER = booleanPreferencesKey("FIRST_ENTER")
    }

    override suspend fun getToken(): Flow<String> {
        return withContext(Dispatchers.IO){
            dataStore.data.map { preferences ->
                preferences[TOKEN] ?: ""
            }
        }
    }

    override suspend fun setToken(token: String) {
        withContext(Dispatchers.IO){
            dataStore.edit { preferences ->
                preferences[TOKEN] = token
            }
        }
    }

    override suspend fun deleteToken() {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs ->
                prefs -= TOKEN
            }
        }
    }

    override suspend fun isFirstEnter(): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                preferences[FIRST_ENTER] ?: true
            }
        }
    }

    override suspend fun setFirstEnter(isFirst: Boolean) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[FIRST_ENTER] = isFirst
            }
        }
    }

}