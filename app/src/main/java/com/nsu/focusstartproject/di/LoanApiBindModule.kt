package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.loans_network.LoanDataSource
import com.nsu.focusstartproject.data.loans_network.LoanRemoteDataSourceImpl
import com.nsu.focusstartproject.data.loans_network.LoanRepositoryImpl
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LoanApiBindModule {

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindLoanRemoteDataSourceImpl_to_loanDataSource(impl: LoanRemoteDataSourceImpl): LoanDataSource

    @Singleton
    @Binds
    @Suppress("FunctionName")
    fun bindLoanRepositoryImpl_to_loanRepository(impl: LoanRepositoryImpl): LoanRepository
}