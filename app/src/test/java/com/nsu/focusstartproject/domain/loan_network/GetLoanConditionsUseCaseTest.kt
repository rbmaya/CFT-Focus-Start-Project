package com.nsu.focusstartproject.domain.loan_network

import com.nsu.focusstartproject.TestEntities.LOAN_CONDITION
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLoanConditionsUseCaseTest {

    private val loanRepository: LoanRepository = mockk {
        coEvery { getLoanConditions() } returns DataStatus.Success(LOAN_CONDITION)
    }

    private val getLoanConditionsUseCase = GetLoanConditionsUseCase(
        loanRepository
    )

    @Test
    fun `get loan conditions EXPECT call get loan conditions from repository`() {
        runBlocking {
            val actual = getLoanConditionsUseCase()
            val expected = DataStatus.Success(LOAN_CONDITION)

            assertEquals(actual, expected)
        }
    }

}