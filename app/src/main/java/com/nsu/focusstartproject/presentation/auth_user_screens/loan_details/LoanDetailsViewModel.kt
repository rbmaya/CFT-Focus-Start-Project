package com.nsu.focusstartproject.presentation.auth_user_screens.loan_details

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.loan_network.GetLoanUseCase
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import com.nsu.focusstartproject.utils.IsoDateFormatter
import com.nsu.focusstartproject.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanDetailsViewModel @Inject constructor(
    private val getLoanUseCase: GetLoanUseCase
) : ViewModel() {

    companion object {
        const val TAG = "LoanDetailsViewModel"
    }

    private val _loanDetailsStatus = MutableLiveData<DataStatus<Loan>>()
    val loanDetailsStatus: LiveData<DataStatus<Loan>> = _loanDetailsStatus

    private val _showGetLoanInfo = LiveEvent()
    val showGetLoanInfo: LiveData<Unit> = _showGetLoanInfo

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            Log.e(TAG, it)
            _loanDetailsStatus.value = DataStatus.Error(code = ErrorCode.UNKNOWN)
        }
    }

    fun loadLoanDetails(id: Long) {
        viewModelScope.launch(excHandler) {
            val loan = getLoanUseCase(id = id)
            _loanDetailsStatus.value = loan
        }
    }

    fun onApprovedLoan() {
        _showGetLoanInfo()
    }

    fun formatLoan(loan: Loan): Loan {
        val newLoan = loan.copy()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formattedDate = IsoDateFormatter.format(loan.date)
            if (formattedDate.isNotEmpty()) {
                newLoan.copy(date = formattedDate)
            } else {
                newLoan
            }
        } else {
            newLoan
        }
    }
}