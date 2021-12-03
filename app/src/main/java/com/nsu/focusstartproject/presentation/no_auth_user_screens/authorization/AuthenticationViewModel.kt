package com.nsu.focusstartproject.presentation.no_auth_user_screens.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.UserInfo
import com.nsu.focusstartproject.domain.auth.SignInUseCase
import com.nsu.focusstartproject.domain.preferences.GetTokenUseCase
import com.nsu.focusstartproject.domain.preferences.SetTokenUseCase
import com.nsu.focusstartproject.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    companion object {
        const val TAG = "AuthViewModel"
    }

    private val _authenticationStatus = MutableLiveData<DataStatus<String>>()
    val authenticationStatus: LiveData<DataStatus<String>> = _authenticationStatus

    private val _navigateToMainScreen = LiveEvent()
    val navigateToMainScreen: LiveData<Unit> = _navigateToMainScreen

    private val _navigateToSignUpScreen = LiveEvent()
    val navigateToSignUpScreen: LiveData<Unit> = _navigateToSignUpScreen

    private val _wrongFieldsEvent = SingleLiveEvent<FieldsError>()
    val wrongFieldsEvent: LiveData<FieldsError> = _wrongFieldsEvent

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            Log.e(TAG, it)
            _authenticationStatus.value = DataStatus.Error(code = ErrorCode.UNKNOWN)
        }
    }

    fun signIn(userName: String, password: String) {
        if (!validateFields(userName = userName, password = password)) {
            return
        }
        _authenticationStatus.value = DataStatus.Loading
        viewModelScope.launch(excHandler) {
            when (val token = signInUseCase(UserInfo(name = userName, password = password))) {
                is DataStatus.Success -> {
                    token.data?.let {
                        setTokenUseCase(it)
                        _authenticationStatus.value = token
                    }
                }
                is DataStatus.Error -> {
                    _authenticationStatus.value = token
                }
            }
        }
    }

    fun onSuccessSignIn() {
        _navigateToMainScreen()
    }

    fun onRegisterButtonClicked() {
        _navigateToSignUpScreen()
    }

    fun checkIsAuthorized() {
        viewModelScope.launch(excHandler) {
            val token = getTokenUseCase()
            if (token.isNotBlank()) {
                _navigateToMainScreen()
            }
        }
    }

    private fun validateFields(userName: String, password: String): Boolean {
        if (userName.isBlank()) {
            _wrongFieldsEvent(FieldsError.EMPTY_USERNAME)
            return false
        }
        if (password.isBlank()) {
            _wrongFieldsEvent(FieldsError.EMPTY_PASSWORD)
            return false
        }
        return true
    }
}