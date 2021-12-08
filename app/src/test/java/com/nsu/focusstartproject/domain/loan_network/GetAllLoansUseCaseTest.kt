package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllLoansUseCaseTest {

    private val loanRepository: LoanRepository = mockk {
        coEvery { getAllLoans() } returns DataStatus.Success(LOANS)
    }

    private val getAllLoansUseCase = GetAllLoansUseCase(
        loanRepository
    )

    @Test
    fun `get all loans EXPECT call get all loans from repository`() {
        runBlocking {
            val expected = DataStatus.Success(LOANS)
            val actual = getAllLoansUseCase()

            assertEquals(expected, actual)
        }
    }
}