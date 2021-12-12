package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.loans_cache.SavedLoanDataSource
import com.nsu.focusstartproject.data.loans_cache.SavedLoanDataSourceImpl
import com.nsu.focusstartproject.data.loans_cache.SavedLoanRepositoryImpl
import com.nsu.focusstartproject.domain.loans_cache.SavedLoanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SavedLoansBindModule {

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindSavedLoanDataSourceImpl_to_savedLoanDataSource(impl: SavedLoanDataSourceImpl): SavedLoanDataSource

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindSavedLoanRepositoryImpl_to_savedLoanRepository(impl: SavedLoanRepositoryImpl): SavedLoanRepository
}