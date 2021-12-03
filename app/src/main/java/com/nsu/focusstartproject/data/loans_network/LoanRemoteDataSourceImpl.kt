package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRemoteDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi
) : LoanDataSource{

    override suspend fun getLoan(id: Int): Response<Loan> {
        return loanApi.getLoan(id = id)
    }

    override suspend fun getAllLoans(): Response<List<Loan>> {
        return loanApi.getAllLoans()
    }
}