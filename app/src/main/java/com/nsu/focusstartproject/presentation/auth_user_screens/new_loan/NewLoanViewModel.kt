package com.nsu.focusstartproject.presentation.auth_user_screens.new_loan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import com.nsu.focusstartproject.domain.loan_network.CreateLoanRequestUseCase
import com.nsu.focusstartproject.domain.loan_network.GetLoanConditionsUseCase
import com.nsu.focusstartproject.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewLoanViewModel @Inject constructor(
    private val createLoanRequestUseCase: CreateLoanRequestUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase
) : ViewModel() {

    companion object {
        const val TAG = "NewLoanViewModel"
    }

    private val _loanConditionsStatus = MutableLiveData<DataStatus<LoanCondition>>()
    val loanConditionsStatus: LiveData<DataStatus<LoanCondition>> = _loanConditionsStatus

    private val _loanRequestStatus = MutableLiveData<DataStatus<Loan>>()
    val loanRequestStatus: LiveData<DataStatus<Loan>> = _loanRequestStatus

    private val _navigateToLoanList = LiveEvent()
    val navigateToLoanList: LiveData<Unit> = _navigateToLoanList

    private val _showSuccessfulRequest = LiveEvent()
    val showSuccessfulRequest: LiveData<Unit> = _showSuccessfulRequest

    private val _wrongFieldsEvent = SingleLiveEvent<FieldsError>()
    val wrongFieldsEvent: LiveData<FieldsError> = _wrongFieldsEvent

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            Log.e(TAG, it)
            _loanRequestStatus.value = DataStatus.Error(code = ErrorCode.UNKNOWN)
        }
    }

    fun initLoanConditions() {
        viewModelScope.launch(excHandler) {
            val conditions = getLoanConditionsUseCase()
            _loanConditionsStatus.value = conditions
        }
    }

    fun onErrorLoadingConditions() {
        _navigateToLoanList()
    }

    fun onSuccessLoanRequest() {
        _showSuccessfulRequest()
        _navigateToLoanList()
    }

    fun createLoanRequest(amount: Int, firstName: String, lastName: String, phoneNumber: String) {
        viewModelScope.launch(excHandler) {
            if (validateFields(amount)) {
                _loanRequestStatus.value = DataStatus.Loading
                val conditions = _loanConditionsStatus.value as DataStatus.Success
                conditions.data?.let {
                    val loanRequest = LoanRequest(
                        amount = amount,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        percent = it.percent,
                        period = it.period
                    )
                    val newLoan = createLoanRequestUseCase(loanRequest = loanRequest)
                    _loanRequestStatus.value = newLoan
                }
            }
        }
    }

    private fun validateFields(amount: Int): Boolean {
        val conditions = _loanConditionsStatus.value
        if (conditions is DataStatus.Success) {
            val data = conditions.data
            data?.let {
                if (amount > it.maxAmount) {
                    _wrongFieldsEvent(FieldsError.LARGE_AMOUNT)
                    return false
                }
            }
        }
        return true
    }

}