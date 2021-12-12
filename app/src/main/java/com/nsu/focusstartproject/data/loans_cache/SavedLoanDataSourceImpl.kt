package com.nsu.focusstartproject.data.loans_cache

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedLoanDataSourceImpl @Inject constructor(
    private val savedLoanDao: SavedLoanDao
) : SavedLoanDataSource {

    override suspend fun saveLoans(list: List<SavedLoan>) {
        return savedLoanDao.saveLoans(list = list)
    }

    override suspend fun getSavedLoans(): List<SavedLoan> {
        return savedLoanDao.getSavedLoans()
    }

    override suspend fun deleteLoans() {
        return savedLoanDao.deleteLoans()
    }
}