package com.nsu.focusstartproject.presentation.auth_user_screens.account_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.preferences.DeleteTokenUseCase
import com.nsu.focusstartproject.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val deleteTokenUseCase: DeleteTokenUseCase
) : ViewModel() {

    private val _signOutEvent = LiveEvent()
    val signOutEvent: LiveData<Unit> = _signOutEvent

    private val _navigateToAuthFragment = LiveEvent()
    val navigateToAuthFragment: LiveData<Unit> = _navigateToAuthFragment

    fun onSignOutButtonClicked() {
        _signOutEvent()
    }

    fun onSignOutConfirm() {
        viewModelScope.launch {
            deleteTokenUseCase()
            _navigateToAuthFragment()
        }
    }
}