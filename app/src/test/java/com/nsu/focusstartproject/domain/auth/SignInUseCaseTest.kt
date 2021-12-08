package com.nsu.focusstartproject.domain.auth

import com.nsu.focusstartproject.TestEntities.TOKEN
import com.nsu.focusstartproject.TestEntities.USER_INFO
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignInUseCaseTest {

    private val authRepository: AuthRepository = mockk {
        coEvery { signIn(USER_INFO) } returns DataStatus.Success(TOKEN)
    }

    private val singInUseCase: SignInUseCase = SignInUseCase(authRepository)

    @Test
    fun `sign in EXPECT call sign in from repository`() {
        runBlocking {
            val actual = singInUseCase(USER_INFO)
            val expected = DataStatus.Success(TOKEN)

            assertEquals(actual, expected)
        }
    }
}