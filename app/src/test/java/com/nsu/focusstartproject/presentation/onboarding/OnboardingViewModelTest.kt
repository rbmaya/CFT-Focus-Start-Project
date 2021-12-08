package com.nsu.focusstartproject.presentation.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nsu.focusstartproject.TestCoroutineRule
import com.nsu.focusstartproject.TestEntities.IS_FIRST_ENTER
import com.nsu.focusstartproject.domain.preferences.PreferencesRepository
import com.nsu.focusstartproject.domain.preferences.SetFirstEnterUseCase
import com.nsu.focusstartproject.presentation.getOrAwaitValue
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OnboardingViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: OnboardingViewModel

    private val preferencesRepository: PreferencesRepository = mockk {
        coEvery { setFirstEnter(IS_FIRST_ENTER) } just runs
    }

    @Before
    fun setUp() {
        viewModel = OnboardingViewModel(
            setFirstEnterUseCase = SetFirstEnterUseCase(preferencesRepository)
        )
    }

    @Test
    fun `on skip button clicked EXPECT set first enter and navigate to main screen`() {
        testCoroutineRule.runBlockingTest {
            viewModel.onSkipButtonClicked()
            coVerify { preferencesRepository.setFirstEnter(IS_FIRST_ENTER) }

            assertEquals(viewModel.navigateToMainScreen.getOrAwaitValue(), Unit)
        }
    }
}