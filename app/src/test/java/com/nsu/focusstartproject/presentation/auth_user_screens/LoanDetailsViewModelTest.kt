package com.nsu.focusstartproject.presentation.auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.domain.loan_network.GetLoanUseCase
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import com.nsu.focusstartproject.presentation.TestEntities.LOAN_1
import com.nsu.focusstartproject.presentation.TestEntities.LOAN_ID
import com.nsu.focusstartproject.presentation.auth_user_screens.loan_details.LoanDetailsViewModel
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
class LoanDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: LoanDetailsViewModel

    private val loanRepository: LoanRepository = mockk {
        coEvery { getLoan(LOAN_ID) } returns DataStatus.Success(LOAN_1)
    }

    @Before
    fun setUp() {
        viewModel = LoanDetailsViewModel(
            getLoanUseCase = GetLoanUseCase(loanRepository)
        )
    }

    @Test
    fun `load loan details EXPECT get loan`() {
        runBlockingTest {
            viewModel.loadLoanDetails(LOAN_ID)

            coVerify {
                loanRepository.getLoan(LOAN_ID)
            }

            val expected = DataStatus.Success(LOAN_1)
            val actual = viewModel.loanDetailsStatus.getOrAwaitValue()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `on approved loan EXPECT show loan info`() {
        viewModel.onApprovedLoan()

        assertEquals(viewModel.showGetLoanInfo.getOrAwaitValue(), Unit)
    }
}