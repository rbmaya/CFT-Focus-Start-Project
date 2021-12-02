package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.auth.AuthDataSource
import com.nsu.focusstartproject.data.auth.AuthRemoteDataSourceImpl
import com.nsu.focusstartproject.data.auth.AuthRepositoryImpl
import com.nsu.focusstartproject.data.preferences.PreferencesDataSource
import com.nsu.focusstartproject.data.preferences.PreferencesDataSourceImpl
import com.nsu.focusstartproject.data.preferences.PreferencesRepositoryImpl
import com.nsu.focusstartproject.domain.auth.AuthRepository
import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesBindModule {

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindPreferencesDataSourceImpl_to_preferencesDataSource(impl: PreferencesDataSourceImpl): PreferencesDataSource

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindPreferencesRepositoryImpl_to_preferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}