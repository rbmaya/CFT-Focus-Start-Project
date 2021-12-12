package com.nsu.focusstartproject.domain.loans_cache

import com.nsu.focusstartproject.TestEntities.LOANS
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveLoansUseCaseTest {

    private val savedLoanRepository: SavedLoanRepository = mockk {
        coEvery { saveLoans(LOANS) } just runs
    }

    private val saveLoansUseCase = SaveLoansUseCase(savedLoanRepository)

    @Test
    fun `save loans EXPECT call save loans from repository`() {
        runBlocking {
            saveLoansUseCase(LOANS)

            coVerify {
                savedLoanRepository.saveLoans(LOANS)
            }
        }
    }

}