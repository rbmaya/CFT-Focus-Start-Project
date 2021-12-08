package com.nsu.focusstartproject.presentation.auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.LOANS
import com.nsu.focusstartproject.TestEntities.LOAN_ID
import com.nsu.focusstartproject.domain.loan_network.GetAllLoansUseCase
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import com.nsu.focusstartproject.presentation.auth_user_screens.loan_list.LoanListViewModel
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @Before
    fun setUp() {
        viewModel = LoanListViewModel(
            getAllLoansUseCase = GetAllLoansUseCase(loanRepository)
        )
    }

    @Test
    fun `load data EXPECT load loan list`() {
        runBlockingTest {
            viewModel.loadData()

            coVerify {
                loanRepository.getAllLoans()
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