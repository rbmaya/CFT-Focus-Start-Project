package com.nsu.focusstartproject.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.preferences.IsDarkModeUseCase
import com.nsu.focusstartproject.utils.LiveEvent
import com.nsu.focusstartproject.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isDarkModeUseCase: IsDarkModeUseCase
) : ViewModel() {

    private val _enableDarkModeEvent = SingleLiveEvent<Boolean>()
    val enableDarkModeEvent: LiveData<Boolean> = _enableDarkModeEvent

    private val _setLightModeEvent = LiveEvent()
    val setLightModeEvent: LiveData<Unit> = _setLightModeEvent

    fun checkIsDarkMode() {
        viewModelScope.launch {
            val isDarkMode = isDarkModeUseCase()
            if (isDarkMode) {
                _enableDarkModeEvent(true)
            } else {
                _enableDarkModeEvent(false)
            }
        }
    }
}