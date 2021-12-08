package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.IS_DARK_MODE
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetDarkModeUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { setDarkMode(IS_DARK_MODE) } just runs
    }

    private val setDarkModeUseCase = SetDarkModeUseCase(
        preferencesRepository
    )

    @Test
    fun `set dark mode EXPECT call set dark mode from repository`() {
        runBlocking {
            setDarkModeUseCase(IS_DARK_MODE)

            coVerify {
                preferencesRepository.setDarkMode(IS_DARK_MODE)
            }
        }
    }

}