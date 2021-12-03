package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanRequest
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateLoanRequestUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {
    suspend operator fun invoke(loanRequest: LoanRequest): DataStatus<Loan> {
        return loanRepository.createLoanRequest(loanRequest = loanRequest)
    }
}