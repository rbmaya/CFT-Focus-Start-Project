package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.TOKEN
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetTokenUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { setToken(TOKEN) } just runs
    }

    private val setTokenUseCase = SetTokenUseCase(
        preferencesRepository
    )

    @Test
    fun `set token EXPECT call set token from repository`() {
        runBlocking {
            setTokenUseCase(TOKEN)

            coVerify { preferencesRepository.setToken(TOKEN) }
        }
    }
}