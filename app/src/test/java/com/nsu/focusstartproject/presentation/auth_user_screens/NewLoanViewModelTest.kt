package com.nsu.focusstartproject.presentation.auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.LOAN_3
import com.nsu.focusstartproject.TestEntities.LOAN_CONDITION
import com.nsu.focusstartproject.TestEntities.LOAN_REQUEST
import com.nsu.focusstartproject.domain.loan_network.CreateLoanRequestUseCase
import com.nsu.focusstartproject.domain.loan_network.GetLoanConditionsUseCase
import com.nsu.focusstartproject.domain.loan_network.LoanRepository
import com.nsu.focusstartproject.presentation.auth_user_screens.new_loan.NewLoanViewModel
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class NewLoanViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: NewLoanViewModel

    private val loanRepository: LoanRepository = mockk {
        coEvery { createLoanRequest(LOAN_REQUEST) } returns DataStatus.Success(LOAN_3)
        coEvery { getLoanConditions() } returns DataStatus.Success(LOAN_CONDITION)
    }

    @Before
    fun setUp() {
        viewModel = NewLoanViewModel(
            createLoanRequestUseCase = CreateLoanRequestUseCase(loanRepository),
            getLoanConditionsUseCase = GetLoanConditionsUseCase(loanRepository)
        )
    }

    @Test
    fun `init loan conditions EXPECT loan loan condition`() {
        runBlockingTest {
            viewModel.initLoanConditions()

            coVerify {
                loanRepository.getLoanConditions()
            }

            val expected = DataStatus.Success(LOAN_CONDITION)
            val actual = viewModel.loanConditionsStatus.getOrAwaitValue()
            assertEquals(actual, expected)
        }
    }

    @Test
    fun `on error loading conditions EXPECT navigate to loan list`() {
        runBlockingTest {
            viewModel.onErrorLoadingConditions()

            assertEquals(viewModel.navigateToLoanList.getOrAwaitValue(), Unit)
        }
    }

    @Test
    fun `on success loan request EXPECT show successfully and navigate to loan list`() {
        runBlockingTest {
            viewModel.onSuccessLoanRequest()

            assertEquals(viewModel.showSuccessfulRequest.getOrAwaitValue(), Unit)
            assertEquals(viewModel.navigateToLoanList.getOrAwaitValue(), Unit)
        }
    }

    @Test
    fun `create loan request with valid data EXPECT call creating loan request`() {
        runBlockingTest {
            viewModel.initLoanConditions()
            viewModel.createLoanRequest(
                amount = LOAN_REQUEST.amount,
                firstName = LOAN_REQUEST.firstName,
                lastName = LOAN_REQUEST.lastName,
                phoneNumber = LOAN_REQUEST.phoneNumber
            )

            coVerify {
                loanRepository.createLoanRequest(LOAN_REQUEST)
            }

            val expected = DataStatus.Success(LOAN_3)
            val actual = viewModel.loanRequestStatus.getOrAwaitValue()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `create loan request with no valid data EXPECT call creating loan request`() {
        runBlockingTest {
            viewModel.createLoanRequest(
                amount = LOAN_REQUEST.amount,
                firstName = "",
                lastName = LOAN_REQUEST.lastName,
                phoneNumber = LOAN_REQUEST.phoneNumber
            )

            coVerify(inverse = true) {
                loanRepository.createLoanRequest(LOAN_REQUEST)
            }
        }
    }


}