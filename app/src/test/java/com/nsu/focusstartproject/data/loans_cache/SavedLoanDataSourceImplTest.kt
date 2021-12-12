package com.nsu.focusstartproject.data.loans_cache

import com.nsu.focusstartproject.TestEntities.SAVED_LOANS
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SavedLoanDataSourceImplTest {

    private val savedLoanDao: SavedLoanDao = mockk {
        coEvery { getSavedLoans() } returns SAVED_LOANS
        coEvery { saveLoans(SAVED_LOANS) } just runs
        coEvery { deleteLoans() } just runs
    }

    private val savedLoansDataSource = SavedLoanDataSourceImpl(
        savedLoanDao
    )

    @Test
    fun `get saved loans EXPECT call get saved loans from DAO`() {
        runBlocking {
            val actual = savedLoansDataSource.getSavedLoans()
            val expected = SAVED_LOANS

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `save loans EXPECT call save loans from DAO`() {
        runBlocking {
            savedLoansDataSource.saveLoans(SAVED_LOANS)

            coVerify {
                savedLoanDao.saveLoans(SAVED_LOANS)
            }
        }
    }

    @Test
    fun `delete loans EXPECT call delete loans from DAO`() {
        runBlocking {
            savedLoansDataSource.deleteLoans()

            coVerify {
                savedLoanDao.deleteLoans()
            }
        }
    }
}