package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.loan_network.GetAllLoansUseCase
import com.nsu.focusstartproject.domain.loans_cache.GetSavedLoansUseCase
import com.nsu.focusstartproject.domain.loans_cache.SaveLoansUseCase
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.LiveEvent
import com.nsu.focusstartproject.utils.SingleLiveEvent
import com.nsu.focusstartproject.utils.errors_processing.toErrorCode
import com.nsu.focusstartproject.utils.fields_processing.IsoDateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanListViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
    private val getSavedLoansUseCase: GetSavedLoansUseCase,
    private val saveLoansUseCase: SaveLoansUseCase
) : ViewModel() {

    companion object {
        const val TAG = "LoanListViewModel"
    }

    private val _loadDataState = MutableLiveData<DataStatus<List<Loan>>>()
    val loadDataState: LiveData<DataStatus<List<Loan>>> = _loadDataState

    private val _navigateToNewLoan = LiveEvent()
    val navigateToNewLoan: LiveData<Unit> = _navigateToNewLoan

    private val _navigateToLoanDetails = SingleLiveEvent<Long>()
    val navigateToLoanDetails: LiveData<Long> = _navigateToLoanDetails

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let { Log.e(TAG, it) }
        throwable.let {
            _loadDataState.value = DataStatus.Error(it.toErrorCode())
        }
    }

    fun loadSavedData() {
        _loadDataState.value = DataStatus.Loading
        viewModelScope.launch {
            val dataStatus = getSavedLoansUseCase()

            _loadDataState.value = dataStatus
            if (dataStatus is DataStatus.Success) {
                val loans = dataStatus.data
                loans?.let {
                    if (it.isEmpty()) {
                        loadData()
                    }
                }
            }
        }
    }

    fun loadData() {
        _loadDataState.value = DataStatus.Loading
        viewModelScope.launch(excHandler) {
            val dataStatus = getAllLoansUseCase()
            _loadDataState.value = dataStatus
            if (dataStatus is DataStatus.Success) {
                dataStatus.data?.let { saveLoansUseCase(it) }
            }
        }
    }

    fun processLoans(loans: List<Loan>): List<Loan> {
        return loans.map { loan ->
            val newLoan = loan.copy()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

    fun onAddLoanButtonClicked() {
        _navigateToNewLoan()
    }

    fun onLoanClicked(id: Long) {
        _navigateToLoanDetails(id)
    }
}