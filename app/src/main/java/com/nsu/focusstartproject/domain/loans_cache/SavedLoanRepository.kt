package com.nsu.focusstartproject.domain.loans_cache

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus

interface SavedLoanRepository {

    suspend fun saveLoans(list: List<Loan>)

    suspend fun getSavedLoans(): DataStatus<List<Loan>>

    suspend fun deleteLoans()
}