package com.nsu.focusstartproject.data.auth

import com.nsu.focusstartproject.TestEntities.TOKEN
import com.nsu.focusstartproject.TestEntities.USER_DTO
import com.nsu.focusstartproject.TestEntities.USER_INFO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AuthRemoteDataSourceImplTest {

    private val authApi: AuthApi = mockk {
        coEvery { signIn(USER_INFO) } returns Response.success(TOKEN)
        coEvery { signUp(USER_INFO) } returns Response.success(USER_DTO)
    }

    private val authDataSource = AuthRemoteDataSourceImpl(authApi)

    @Test
    fun `sign in EXPECT call sign in from API`() {
        runBlocking {
            val actual = authDataSource.signIn(USER_INFO).body()
            val expected = TOKEN

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `sign up EXPECT call sign up from API`() {
        runBlocking {
            val actual = authDataSource.signUp(USER_INFO).body()
            val expected = USER_DTO

            assertEquals(actual, expected)
        }
    }
}