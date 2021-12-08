package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.TestEntities.USER_INFO
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpUseCaseTest {

    private val authRepository: AuthRepository = mockk {
        coEvery { signUp(USER_INFO) } returns DataStatus.Success()
    }

    private val signUpUseCase = SignUpUseCase(authRepository)

    @Test
    fun `sign up EXPECT call sign up from repository`() {
        runBlocking {
            signUpUseCase(USER_INFO)

            coVerify {
                authRepository.signUp(USER_INFO)
            }
        }
    }
}