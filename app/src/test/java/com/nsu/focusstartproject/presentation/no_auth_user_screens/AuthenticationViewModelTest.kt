package com.nsu.focusstartproject.presentation.no_auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.IS_FIRST_ENTER
import com.nsu.focusstartproject.TestEntities.NO_VALID_USER_INFO
import com.nsu.focusstartproject.TestEntities.TOKEN
import com.nsu.focusstartproject.TestEntities.USER_INFO
import com.nsu.focusstartproject.domain.auth.AuthRepository
import com.nsu.focusstartproject.domain.auth.SignInUseCase
import com.nsu.focusstartproject.domain.preferences.GetTokenUseCase
import com.nsu.focusstartproject.domain.preferences.IsFirstEnterUseCase
import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import com.nsu.focusstartproject.domain.preferences.SetTokenUseCase
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import com.nsu.focusstartproject.presentation.no_auth_user_screens.authorization.AuthenticationViewModel
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
class AuthenticationViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: AuthenticationViewModel

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { getToken() } returns TOKEN
        coEvery { setToken(TOKEN) } just runs
        coEvery { isFirstEnter() } returns IS_FIRST_ENTER
    }

    private val authRepository: AuthRepository = mockk {
        coEvery { signIn(USER_INFO) } returns DataStatus.Success(TOKEN)
    }

    @Before
    fun setUp() {
        viewModel = AuthenticationViewModel(
            getTokenUseCase = GetTokenUseCase(preferencesRepository),
            setTokenUseCase = SetTokenUseCase(preferencesRepository),
            signInUseCase = SignInUseCase(authRepository),
            isFirstEnterUseCase = IsFirstEnterUseCase(preferencesRepository)
        )
    }

    @Test
    fun `sign in with valid data EXPECT sign in and set token`() {
        runBlockingTest {
            viewModel.signIn(USER_INFO.name, USER_INFO.password)

            coVerifyOrder {
                authRepository.signIn(USER_INFO)
                preferencesRepository.setToken(TOKEN)
            }

            val expected = DataStatus.Success(TOKEN)
            val actual = viewModel.authenticationStatus.getOrAwaitValue()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `sign in with no valid data EXPECT no calls`() {
        runBlockingTest {
            viewModel.signIn(NO_VALID_USER_INFO.name, NO_VALID_USER_INFO.password)
            coVerify(inverse = true) {
                authRepository.signIn(NO_VALID_USER_INFO)
                preferencesRepository.setToken(TOKEN)
            }
        }
    }

    @Test
    fun `on success sign in with no first enter EXPECT call checking first enter and navigate to main screen`() {
        runBlockingTest {
            viewModel.onSuccessSignIn()

            coVerify {
                preferencesRepository.isFirstEnter()
            }

            assertEquals(viewModel.navigateToMainScreen.getOrAwaitValue(), Unit)
        }
    }

    @Test
    fun `on registerButton clicked EXPECT navigate to sign up screen`() {
        viewModel.onRegisterButtonClicked()

        assertEquals(viewModel.navigateToSignUpScreen.getOrAwaitValue(), Unit)
    }

    @Test
    fun `check is authorized with auth status EXPECT check token and navigate to main screen`() {
        runBlockingTest {
            viewModel.checkIsAuthorized()

            coVerify {
                preferencesRepository.getToken()
            }

            assertEquals(viewModel.navigateToMainScreen.getOrAwaitValue(), Unit)
        }
    }
}