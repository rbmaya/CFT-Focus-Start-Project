package com.nsu.focusstartproject.domain.loans_cache

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSavedLoansUseCase @Inject constructor(
    private val savedLoanRepository: SavedLoanRepository
) {

    suspend operator fun invoke(): DataStatus<List<Loan>> {
        return savedLoanRepository.getSavedLoans()
    }
}