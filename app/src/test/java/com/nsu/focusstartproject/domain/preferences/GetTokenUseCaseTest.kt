package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.TOKEN
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTokenUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { getToken() } returns TOKEN
    }

    private val getTokenUseCase = GetTokenUseCase(
        preferencesRepository
    )

    @Test
    fun `get token EXPECT call get token from repository`() {
        runBlocking {
            val actual = getTokenUseCase()
            val expected = TOKEN

            assertEquals(actual, expected)
        }
    }

}