package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.TestEntities.LOAN_1
import com.nsu.focusstartproject.TestEntities.LOAN_CONDITION
import com.nsu.focusstartproject.TestEntities.LOAN_ID
import com.nsu.focusstartproject.TestEntities.LOAN_REQUEST
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class LoanRemoteDataSourceImplTest {

    private val loanApi: LoanApi = mockk {
        coEvery { getLoan(LOAN_ID) } returns Response.success(LOAN_1)
        coEvery { getAllLoans() } returns Response.success(LOANS)
        coEvery { createLoanRequest(LOAN_REQUEST) } returns Response.success(
            LOAN_1
        )
        coEvery { getLoanConditions() } returns Response.success(LOAN_CONDITION)
    }

    private val loanDataSource = LoanRemoteDataSourceImpl(loanApi)

    @Test
    fun `get loan EXPECT call get loan from API`() {
        runBlocking {
            val actual = loanDataSource.getLoan(LOAN_ID).body()
            val expected = LOAN_1

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `get all loans EXPECT call get all loans from API`() {
        runBlocking {
            val actual = loanDataSource.getAllLoans().body()
            val expected = LOANS

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `create loan request EXPECT call create loan request from API`() {
        runBlocking {
            val actual = loanDataSource.createLoanRequest(LOAN_REQUEST).body()
            val expected = LOAN_1

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `get loan conditions EXPECT call get loan conditions from API`() {
        runBlocking {
            val actual = loanDataSource.getLoanConditions().body()
            val expected = LOAN_CONDITION

            assertEquals(actual, expected)
        }
    }
}