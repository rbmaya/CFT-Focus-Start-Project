package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.auth.AuthDataSource
import com.nsu.focusstartproject.data.auth.AuthRemoteDataSourceImpl
import com.nsu.focusstartproject.data.auth.AuthRepositoryImpl
import com.nsu.focusstartproject.domain.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthApiBindModule {

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindAuthRemoteDataSourceImpl_to_authDataSource(impl: AuthRemoteDataSourceImpl): AuthDataSource

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindAuthRepositoryImpl_to_authRepository(impl: AuthRepositoryImpl): AuthRepository
}