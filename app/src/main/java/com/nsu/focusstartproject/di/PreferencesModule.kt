package com.nsu.focusstartproject.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        val dataStore = PreferenceDataStoreFactory.create(
            migrations = listOf(
                SharedPreferencesMigration(context, "app_prefs")
            ),
            scope = CoroutineScope(Dispatchers.Default),
        ) {
            context.preferencesDataStoreFile("app_prefs")
        }
        return dataStore
    }

}