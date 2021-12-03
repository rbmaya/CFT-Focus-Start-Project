package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllLoansUseCase @Inject constructor(
    private val loanRepository: LoanRepository
){

    suspend operator fun invoke(): DataStatus<List<Loan>> = loanRepository.getAllLoans()
}