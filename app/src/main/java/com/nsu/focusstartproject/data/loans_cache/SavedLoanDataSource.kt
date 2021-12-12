package com.nsu.focusstartproject.data.loans_cache

interface SavedLoanDataSource {
    suspend fun saveLoans(list: List<SavedLoan>)

    suspend fun getSavedLoans(): List<SavedLoan>

    suspend fun deleteLoans()
}