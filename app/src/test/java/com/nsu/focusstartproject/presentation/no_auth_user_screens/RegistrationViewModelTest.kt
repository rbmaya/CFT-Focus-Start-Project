package com.nsu.focusstartproject.presentation.no_auth_user_screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.NO_VALID_USER_INFO
import com.nsu.focusstartproject.TestEntities.USER_INFO
import com.nsu.focusstartproject.domain.auth.AuthRepository
import com.nsu.focusstartproject.domain.auth.SignUpUseCase
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import com.nsu.focusstartproject.presentation.no_auth_user_screens.registration.RegistrationViewModel
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegistrationViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: RegistrationViewModel

    private val authRepository: AuthRepository = mockk {
        coEvery { signUp(USER_INFO) } returns DataStatus.Success()
    }

    @Before
    fun setUp() {
        viewModel = RegistrationViewModel(
            signUpUseCase = SignUpUseCase(authRepository)
        )
    }

    @Test
    fun `sign up with valid data EXPECT sign up`() {
        runBlocking {
            viewModel.signUp(USER_INFO.name, USER_INFO.password)

            coVerify {
                authRepository.signUp(USER_INFO)
            }

            assertEquals(viewModel.registrationStatus.getOrAwaitValue(), DataStatus.Success<Any>())
        }
    }

    @Test
    fun `sign up with no valid data EXPECT no sign up`() {
        runBlocking {
            viewModel.signUp(NO_VALID_USER_INFO.name, NO_VALID_USER_INFO.password)

            coVerify(inverse = true) {
                authRepository.signUp(NO_VALID_USER_INFO)
            }
        }
    }

    @Test
    fun `on success sign up EXPECT navigate to auth screen`() {
        viewModel.onSuccessSignUp()
        assertEquals(viewModel.navigateToAuthentication.getOrAwaitValue(), Unit)
    }


}