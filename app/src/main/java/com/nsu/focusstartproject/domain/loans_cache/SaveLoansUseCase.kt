package com.nsu.focusstartproject.domain.loans_cache

import com.nsu.focusstartproject.domain.Loan
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveLoansUseCase @Inject constructor(
    private val savedLoanRepository: SavedLoanRepository
) {

    suspend operator fun invoke(list: List<Loan>) {
        return savedLoanRepository.saveLoans(list = list)
    }
}