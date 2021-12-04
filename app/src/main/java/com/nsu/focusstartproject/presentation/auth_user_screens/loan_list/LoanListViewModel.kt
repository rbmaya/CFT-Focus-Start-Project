package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.loan_network.GetAllLoansUseCase
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import com.nsu.focusstartproject.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LoanListViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase
) : ViewModel() {

    companion object {
        const val TAG = "LoanListViewModel"
    }

    private val _loadDataState = MutableLiveData<DataStatus<List<Loan>>>()
    val loadDataState: LiveData<DataStatus<List<Loan>>> = _loadDataState

    private val _navigateToNewLoan = LiveEvent()
    val navigateToNewLoan: LiveData<Unit> = _navigateToNewLoan

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            Log.e(TAG, it)
            _loadDataState.value = DataStatus.Error(code = ErrorCode.UNKNOWN)
        }
    }

    fun loadData() {
        _loadDataState.value = DataStatus.Loading
        viewModelScope.launch(excHandler) {
            val data = getAllLoansUseCase()
            _loadDataState.value = data
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun processLoans(loans: List<Loan>): List<Loan> {
        return loans.map { loan ->
            try {
                val parsedDate = LocalDateTime.parse(loan.date, DateTimeFormatter.ISO_DATE_TIME)
                val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                formattedDate?.let {
                    loan.copy(date = formattedDate)
                } ?: loan
            } catch (exc: Exception) {
                loan
            }
        }
    }

    fun onAddLoanButtonClicked() {
        _navigateToNewLoan()
    }
}