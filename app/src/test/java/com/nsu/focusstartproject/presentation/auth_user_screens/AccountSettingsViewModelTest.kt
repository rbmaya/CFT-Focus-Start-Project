package com.nsu.focusstartproject.presentation.auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.IS_DARK_MODE
import com.nsu.focusstartproject.domain.loans_cache.DeleteLoansUseCase
import com.nsu.focusstartproject.domain.loans_cache.SavedLoanRepository
import com.nsu.focusstartproject.domain.preferences.DeleteTokenUseCase
import com.nsu.focusstartproject.domain.preferences.IsDarkModeUseCase
import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import com.nsu.focusstartproject.domain.preferences.SetDarkModeUseCase
import com.nsu.focusstartproject.presentation.auth_user_screens.account_settings.AccountSettingsViewModel
import com.nsu.focusstartproject.presentation.getOrAwaitValue
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
class AccountSettingsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: AccountSettingsViewModel

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { deleteToken() } just runs
        coEvery { isDarkMode() } returns IS_DARK_MODE
        coEvery { setDarkMode(IS_DARK_MODE) } just runs
    }

    private val savedLoanRepository: SavedLoanRepository = mockk {
        coEvery { deleteLoans() } just runs
    }

    @Before
    fun setUp() {
        viewModel = AccountSettingsViewModel(
            deleteTokenUseCase = DeleteTokenUseCase(preferencesRepository),
            isDarkModeUseCase = IsDarkModeUseCase(preferencesRepository),
            setDarkModeUseCase = SetDarkModeUseCase(preferencesRepository),
            deleteLoansUseCase = DeleteLoansUseCase(savedLoanRepository)
        )
    }

    @Test
    fun `on signOutButton clicked EXPECT sign out event`() {
        viewModel.onSignOutButtonClicked()

        assertEquals(viewModel.signOutEvent.getOrAwaitValue(), Unit)
    }

    @Test
    fun `on sign out confirm EXPECT delete token and navigate to auth screen`() {
        runBlockingTest {
            viewModel.onSignOutConfirm()

            coVerifySequence {
                preferencesRepository.deleteToken()
                savedLoanRepository.deleteLoans()
            }

            assertEquals(viewModel.navigateToAuthFragment.getOrAwaitValue(), Unit)
        }
    }

    @Test
    fun `on switch dark mode EXPECT set dark mode and enable dark mode`() {
        runBlockingTest {
            viewModel.onSwitchDarkMode(IS_DARK_MODE)

            coVerify {
                preferencesRepository.setDarkMode(IS_DARK_MODE)
            }

            assertEquals(viewModel.enableDarkMode.getOrAwaitValue(), IS_DARK_MODE)
        }
    }

    @Test
    fun `init switch dark mode EXPECT check isDarkMode and enable dark mode`() {
        runBlockingTest {
            viewModel.initSwitchDarkMode()

            coVerify {
                preferencesRepository.isDarkMode()
            }

            assertEquals(viewModel.enableDarkMode.getOrAwaitValue(), IS_DARK_MODE)
        }
    }
}