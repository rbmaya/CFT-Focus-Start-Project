package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLoanUseCase @Inject constructor(
    private val loanRepository: LoanRepository
){
    suspend operator fun invoke(id: Long): DataStatus<Loan> = loanRepository.getLoan(id = id)
}