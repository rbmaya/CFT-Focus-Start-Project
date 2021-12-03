package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import com.nsu.focusstartproject.utils.DataStatus

interface LoanRepository {

    suspend fun getLoan(id: Int): DataStatus<Loan>

    suspend fun getAllLoans(): DataStatus<List<Loan>>

    suspend fun createLoanRequest(loanRequest: LoanRequest): DataStatus<Loan>

    suspend fun getLoanConditions(): DataStatus<LoanCondition>
}