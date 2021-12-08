package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.TestEntities.TOKEN
import com.nsu.focusstartproject.TestEntities.USER_DTO
import com.nsu.focusstartproject.TestEntities.USER_INFO
import com.nsu.focusstartproject.utils.DataStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {

    private val dataSource: AuthDataSource = mockk {
        coEvery { signIn(USER_INFO) } returns Response.success(TOKEN)
        coEvery { signUp(USER_INFO) } returns Response.success(USER_DTO)
    }

    private val authRepository = AuthRepositoryImpl(dataSource)

    @Test
    fun `sign in EXPECT call sign in from datasource`() {
        runBlocking {
            val actual = authRepository.signIn(USER_INFO)
            val expected = DataStatus.Success(TOKEN)

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `sign up EXPECT call sign up from datasource`() {
        runBlocking {
            val actual = authRepository.signUp(USER_INFO)
            val expected = DataStatus.Success(USER_DTO)

            assertEquals(actual, expected)
        }
    }
}