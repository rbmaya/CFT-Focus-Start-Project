package com.nsu.focusstartproject.presentation.no_auth_user_screens.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.UserInfo
import com.nsu.focusstartproject.domain.auth.SignUpUseCase
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.FieldsError
import com.nsu.focusstartproject.utils.LiveEvent
import com.nsu.focusstartproject.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    companion object{
        const val TAG = "RegistrationViewModel"
    }

    private val _registrationStatus: MutableLiveData<DataStatus<Any>> = MutableLiveData()
    val registrationStatus: LiveData<DataStatus<Any>> = _registrationStatus

    private val _navigateToAuthentication = LiveEvent()
    val navigateToAuthentication: LiveData<Unit> = _navigateToAuthentication

    private val _wrongFieldsEvent = SingleLiveEvent<FieldsError>()
    val wrongFieldsEvent: LiveData<FieldsError> = _wrongFieldsEvent

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let { Log.e(TAG, it) }
    }

    fun signUp(userName: String, password: String){
        if (!validateFields(userName = userName, password = password)){
            return
        }
        _registrationStatus.value = DataStatus.Loading
        viewModelScope.launch(excHandler){
            _registrationStatus.value = signUpUseCase(UserInfo(name = userName, password = password))
        }
    }

    fun onSuccessSignUp(){
        _navigateToAuthentication()
    }

    private fun validateFields(userName: String, password: String): Boolean{
        if (userName.isBlank()){
            _wrongFieldsEvent(FieldsError.EMPTY_USERNAME)
            return false
        }
        if (password.isBlank()){
            _wrongFieldsEvent(FieldsError.EMPTY_PASSWORD)
            return false
        }
        return true
    }



}