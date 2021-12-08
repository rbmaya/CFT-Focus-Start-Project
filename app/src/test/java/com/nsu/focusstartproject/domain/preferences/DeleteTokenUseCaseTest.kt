package com.nsu.focusstartproject.domain.preferences

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteTokenUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { deleteToken() } just runs
    }

    private val deleteTokenUseCase = DeleteTokenUseCase(
        preferencesRepository
    )

    @Test
    fun `delete token EXPECT delete token from repository`() {
        runBlocking {
            deleteTokenUseCase()

            coVerify { preferencesRepository.deleteToken() }
        }
    }
}