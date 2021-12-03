package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus

interface LoanRepository {

    suspend fun getLoan(id: Int): DataStatus<Loan>

    suspend fun getAllLoans(): DataStatus<List<Loan>>
}