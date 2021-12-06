package com.nsu.focusstartproject.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.preferences.SetFirstEnterUseCase
import com.nsu.focusstartproject.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setFirstEnterUseCase: SetFirstEnterUseCase
) : ViewModel() {

    private val _navigateToMainScreen = LiveEvent()
    val navigateToMainScreen: LiveData<Unit> = _navigateToMainScreen

    fun onSkipButtonClicked() {
        viewModelScope.launch {
            setFirstEnterUseCase(false)
            _navigateToMainScreen()
        }
    }

}