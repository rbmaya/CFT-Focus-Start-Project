package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.IS_DARK_MODE
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IsDarkModeUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { isDarkMode() } returns IS_DARK_MODE
    }

    private val isDarkModeUseCase = IsDarkModeUseCase(
        preferencesRepository
    )

    @Test
    fun `call isDarkMode EXPECT call isDarkMode from repository`() {
        runBlocking {
            val actual = isDarkModeUseCase()
            val expected = IS_DARK_MODE

            assertEquals(actual, expected)
        }
    }


}