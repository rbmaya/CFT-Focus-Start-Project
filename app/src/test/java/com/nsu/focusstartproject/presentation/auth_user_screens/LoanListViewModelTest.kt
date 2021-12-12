package com.nsu.focusstartproject.presentation.auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.TestEntities.LOAN_ID
import com.nsu.focusstartproject.domain.loan_network.GetAllLoansUseCase
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import com.nsu.focusstartproject.domain.loans_cache.GetSavedLoansUseCase
import com.nsu.focusstartproject.domain.loans_cache.SaveLoansUseCase
import com.nsu.focusstartproject.domain.loans_cache.SavedLoanRepository
import com.nsu.focusstartproject.presentation.auth_user_screens.loan_list.LoanListViewModel
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoanListViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: LoanListViewModel

    private val loanRepository: LoanRepository = mockk {
        coEvery { getAllLoans() } returns DataStatus.Success(LOANS)
    }

    private val savedLoanRepository: SavedLoanRepository = mockk {
        coEvery { saveLoans(LOANS) } just runs
        coEvery { getSavedLoans() } returns DataStatus.Success(LOANS)
    }

    @Before
    fun setUp() {
        viewModel = LoanListViewModel(
            getAllLoansUseCase = GetAllLoansUseCase(loanRepository),
            getSavedLoansUseCase = GetSavedLoansUseCase(savedLoanRepository),
            saveLoansUseCase = SaveLoansUseCase(savedLoanRepository)
        )
    }

    @Test
    fun `loan saved data EXPECT load saved loan list`() {
        runBlockingTest {
            viewModel.loadSavedData()

            coVerify {
                savedLoanRepository.getSavedLoans()
            }

            val expected = DataStatus.Success(LOANS)
            val actual = viewModel.loadDataState.getOrAwaitValue()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `load data EXPECT load loan list`() {
        runBlockingTest {
            viewModel.loadData()

            coVerifySequence {
                loanRepository.getAllLoans()
                savedLoanRepository.saveLoans(LOANS)
            }

            val expected = DataStatus.Success(LOANS)
            val actual = viewModel.loadDataState.getOrAwaitValue()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `on addLoanButton clicked EXPECT navigate to new loan`() {
        viewModel.onAddLoanButtonClicked()
        assertEquals(viewModel.navigateToNewLoan.getOrAwaitValue(), Unit)
    }

    @Test
    fun `on loan clicked EXPECT navigate to loan details`() {
        viewModel.onLoanClicked(LOAN_ID)
        assertEquals(viewModel.navigateToLoanDetails.getOrAwaitValue(), LOAN_ID)
    }


}