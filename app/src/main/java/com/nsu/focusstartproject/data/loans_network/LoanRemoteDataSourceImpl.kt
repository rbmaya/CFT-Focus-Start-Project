package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRemoteDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi
) : LoanDataSource {

    override suspend fun getLoan(id: Long): Response<Loan> {
        return loanApi.getLoan(id = id)
    }

    override suspend fun getAllLoans(): Response<List<Loan>> {
        return loanApi.getAllLoans()
    }

    override suspend fun createLoanRequest(loanRequest: LoanRequest): Response<Loan> {
        return loanApi.createLoanRequest(loanRequest = loanRequest)
    }

    override suspend fun getLoanConditions(): Response<LoanCondition> {
        return loanApi.getLoanConditions()
    }
}