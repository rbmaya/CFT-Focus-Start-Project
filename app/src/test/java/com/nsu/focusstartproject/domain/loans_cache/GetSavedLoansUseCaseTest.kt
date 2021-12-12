package com.nsu.focusstartproject.domain.loans_cache

import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSavedLoansUseCaseTest {

    private val savedLoanRepository: SavedLoanRepository = mockk {
        coEvery { getSavedLoans() } returns DataStatus.Success(LOANS)
    }

    private val getSavedLoansUseCase = GetSavedLoansUseCase(savedLoanRepository)

    @Test
    fun `get saved loans EXPECT call get saved loans from repository`() {
        runBlocking {
            val actual = getSavedLoansUseCase()
            val expected = DataStatus.Success(LOANS)

            assertEquals(actual, expected)
        }
    }
}