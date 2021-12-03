package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLoanConditionsUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {
    suspend operator fun invoke(): DataStatus<LoanCondition> {
        return loanRepository.getLoanConditions()
    }
}