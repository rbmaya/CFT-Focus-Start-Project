package com.nsu.focusstartproject.data.loans_cache

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.loans_cache.SavedLoanRepository
import com.nsu.focusstartproject.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedLoanRepositoryImpl @Inject constructor(
    private val savedLoanDataSource: SavedLoanDataSource
) : SavedLoanRepository {

    override suspend fun saveLoans(list: List<Loan>) {
        withContext(Dispatchers.IO) {
            val listSavedLoans = list.map {
                it.toSavedLoan()
            }
            savedLoanDataSource.saveLoans(
                listSavedLoans
            )
        }
    }

    override suspend fun getSavedLoans(): DataStatus<List<Loan>> {
        return withContext(Dispatchers.IO) {
            val loans = savedLoanDataSource.getSavedLoans().map {
                it.toLoan()
            }
            DataStatus.Success(loans)
        }
    }

    override suspend fun deleteLoans() {
        withContext(Dispatchers.IO) {
            savedLoanDataSource.deleteLoans()
        }
    }
}