package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.TestEntities.LOAN_1
import com.nsu.focusstartproject.TestEntities.LOAN_ID
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLoanUseCaseTest {

    private val loanRepository: LoanRepository = mockk {
        coEvery { getLoan(LOAN_ID) } returns DataStatus.Success(LOAN_1)
    }

    private val getLoanUseCase = GetLoanUseCase(
        loanRepository
    )

    @Test
    fun `get loan EXPECT call get loan from repository`() {
        runBlocking {
            val actual = getLoanUseCase(LOAN_ID)
            val expected = DataStatus.Success(LOAN_1)

            assertEquals(actual, expected)
        }
    }

}