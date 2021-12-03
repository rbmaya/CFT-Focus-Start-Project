package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.toDataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRepositoryImpl @Inject constructor(
    private val loanDataSource: LoanDataSource
) : LoanRepository {

    override suspend fun getLoan(id: Int): DataStatus<Loan> {
        return withContext(Dispatchers.IO) {
            val response = loanDataSource.getLoan(id = id)
            response.toDataStatus()
        }
    }

    override suspend fun getAllLoans(): DataStatus<List<Loan>> {
        return withContext(Dispatchers.IO){
            val response = loanDataSource.getAllLoans()
            response.toDataStatus()
        }
    }

}