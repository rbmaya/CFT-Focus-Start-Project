package com.nsu.focusstartproject.domain.loans_cache

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteLoansUseCaseTest {

    private val savedLoanRepository: SavedLoanRepository = mockk {
        coEvery { deleteLoans() } just runs
    }

    private val deleteLoansUseCase = DeleteLoansUseCase(savedLoanRepository)

    @Test
    fun `delete loans EXPECT call delete loans from repository`() {
        runBlocking {
            deleteLoansUseCase()

            coVerify {
                savedLoanRepository.deleteLoans()
            }
        }
    }
}