package com.nsu.focusstartproject.domain.preferences

import com.nsu.focusstartproject.TestEntities.IS_FIRST_ENTER
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetFirstEnterUseCaseTest {

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { setFirstEnter(IS_FIRST_ENTER) } just runs
    }

    private val setFirstEnterUseCase = SetFirstEnterUseCase(
        preferencesRepository
    )

    @Test
    fun `set firstEnter EXPECT call setFirstEnter from repository`() {
        runBlocking {
            setFirstEnterUseCase(IS_FIRST_ENTER)

            coVerify {
                preferencesRepository.setFirstEnter(IS_FIRST_ENTER)
            }
        }
    }

}