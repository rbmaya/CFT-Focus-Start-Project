package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import retrofit2.Response

interface LoanDataSource {

    suspend fun getLoan(id: Long): Response<Loan>

    suspend fun getAllLoans(): Response<List<Loan>>

    suspend fun createLoanRequest(loanRequest: LoanRequest): Response<Loan>

    suspend fun getLoanConditions(): Response<LoanCondition>
}