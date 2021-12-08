package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.TestEntities.LOAN_1
import com.nsu.focusstartproject.TestEntities.LOAN_REQUEST
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateLoanRequestUseCaseTest {

    private val loanRepository: LoanRepository = mockk {
        coEvery { createLoanRequest(LOAN_REQUEST) } returns DataStatus.Success(LOAN_1)
    }

    private val createLoanRequestUseCase = CreateLoanRequestUseCase(
        loanRepository
    )

    @Test
    fun `create loan request EXPECT call create loan from repository`() {
        runBlocking {
            val actual = createLoanRequestUseCase(LOAN_REQUEST)
            val expected = DataStatus.Success(LOAN_1)

            assertEquals(actual, expected)
        }
    }
}