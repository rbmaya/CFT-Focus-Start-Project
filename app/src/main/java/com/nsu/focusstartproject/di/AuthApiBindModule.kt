package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.AuthDataSource
import com.nsu.focusstartproject.data.AuthRemoteDataSourceImpl
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


}