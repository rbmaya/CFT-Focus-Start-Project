package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import retrofit2.Response

interface LoanDataSource {

    suspend fun getLoan(id: Int): Response<Loan>

    suspend fun getAllLoans(): Response<List<Loan>>
}