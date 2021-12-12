package com.nsu.focusstartproject.presentation.auth_user_screens.account_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.loans_cache.DeleteLoansUseCase
import com.nsu.focusstartproject.domain.preferences.DeleteTokenUseCase
import com.nsu.focusstartproject.domain.preferences.IsDarkModeUseCase
import com.nsu.focusstartproject.domain.preferences.SetDarkModeUseCase
import com.nsu.focusstartproject.utils.LiveEvent
import com.nsu.focusstartproject.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val isDarkModeUseCase: IsDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val deleteLoansUseCase: DeleteLoansUseCase
) : ViewModel() {

    private val _signOutEvent = LiveEvent()
    val signOutEvent: LiveData<Unit> = _signOutEvent

    private val _navigateToAuthFragment = LiveEvent()
    val navigateToAuthFragment: LiveData<Unit> = _navigateToAuthFragment

    private val _enableDarkMode = SingleLiveEvent<Boolean>()
    val enableDarkMode: LiveData<Boolean> = _enableDarkMode

    fun onSignOutButtonClicked() {
        _signOutEvent()
    }

    fun onSignOutConfirm() {
        viewModelScope.launch {
            deleteTokenUseCase()
            deleteLoansUseCase()
            _navigateToAuthFragment()
        }
    }

    fun onSwitchDarkMode(isChecked: Boolean) {
        viewModelScope.launch {
            setDarkModeUseCase(isChecked)
            if (isChecked) {
                _enableDarkMode(true)
            } else {
                _enableDarkMode(false)
            }
        }
    }

    fun initSwitchDarkMode() {
        viewModelScope.launch {
            val isDarkMode = isDarkModeUseCase()
            if (isDarkMode) {
                _enableDarkMode(true)
            } else {
                _enableDarkMode(false)
            }
        }
    }
}