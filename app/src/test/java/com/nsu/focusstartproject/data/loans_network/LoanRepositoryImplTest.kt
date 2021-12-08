package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.TestEntities.LOAN_1
import com.nsu.focusstartproject.TestEntities.LOAN_CONDITION
import com.nsu.focusstartproject.TestEntities.LOAN_ID
import com.nsu.focusstartproject.TestEntities.LOAN_REQUEST
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class LoanRepositoryImplTest {

    private val loanDataSource: LoanDataSource = mockk {
        coEvery { getLoan(LOAN_ID) } returns Response.success(LOAN_1)
        coEvery { getAllLoans() } returns Response.success(LOANS)
        coEvery { createLoanRequest(LOAN_REQUEST) } returns Response.success(LOAN_1)
        coEvery { getLoanConditions() } returns Response.success(LOAN_CONDITION)
    }

    private val loanRepository = LoanRepositoryImpl(
        loanDataSource
    )

    @Test
    fun `get loan EXPECT call get loan from datasource`() {
        runBlocking {
            val actual = loanRepository.getLoan(LOAN_ID)
            val expected = DataStatus.Success(LOAN_1)

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `get all loans EXPECT call get all loans from datasource`() {
        runBlocking {
            val actual = loanRepository.getAllLoans()
            val expected = DataStatus.Success(LOANS)

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `create loan request EXPECT call create loan request from datasource`() {
        runBlocking {
            val actual = loanRepository.createLoanRequest(LOAN_REQUEST)
            val expected = DataStatus.Success(LOAN_1)

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `get loan conditions EXPECT call get loan conditions from datasource`() {
        runBlocking {
            val actual = loanRepository.getLoanConditions()
            val expected = DataStatus.Success(LOAN_CONDITION)

            assertEquals(actual, expected)
        }
    }
}