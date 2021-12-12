package com.nsu.focusstartproject.data.loans_cache

import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.TestEntities.SAVED_LOANS
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SavedLoanRepositoryImplTest {

    private val dataSource: SavedLoanDataSource = mockk {
        coEvery { getSavedLoans() } returns SAVED_LOANS
        coEvery { saveLoans(SAVED_LOANS) } just runs
        coEvery { deleteLoans() } just runs
    }

    private val savedLoanRepository = SavedLoanRepositoryImpl(dataSource)

    @Test
    fun `get saved loans EXPECT call get saved loans from datasource`() {
        runBlocking {
            val actual = savedLoanRepository.getSavedLoans()
            val expected = DataStatus.Success(LOANS)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `save loans EXPECT call save loans from datasource`() {
        runBlocking {
            savedLoanRepository.saveLoans(LOANS)

            coVerify {
                dataSource.saveLoans(SAVED_LOANS)
            }
        }
    }

    @Test
    fun `delete loans EXPECT call delete loans from datasource`() {
        runBlocking {
            savedLoanRepository.deleteLoans()

            coVerify {
                dataSource.deleteLoans()
            }
        }
    }
}