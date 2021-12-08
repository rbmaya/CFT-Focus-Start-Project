package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.IS_FIRST_ENTER
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IsFirstEnterUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { isFirstEnter() } returns IS_FIRST_ENTER
    }

    private val isFirstEnterUseCase = IsFirstEnterUseCase(
        preferencesRepository
    )

    @Test
    fun `call isFirstEnter EXPECT call isFirstEnter from repository`() {
        runBlocking {
            val actual = isFirstEnterUseCase()
            val expected = IS_FIRST_ENTER

            assertEquals(actual, expected)
        }
    }
}