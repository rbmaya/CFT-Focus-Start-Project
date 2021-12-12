package com.nsu.focusstartproject.domain.loans_cache

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteLoansUseCase @Inject constructor(
    private val savedLoanRepository: SavedLoanRepository
) {

    suspend operator fun invoke() {
        savedLoanRepository.deleteLoans()
    }
}