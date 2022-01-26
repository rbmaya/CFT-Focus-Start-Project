package com.nsu.focusstartproject.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.domain.loan_network.GetAllLoansUseCase
import com.nsu.focusstartproject.domain.preferences.IsDarkModeUseCase
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.LoansNotificationWorker
import com.nsu.focusstartproject.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val isDarkModeUseCase: IsDarkModeUseCase,
    private val getAllLoansUseCase: GetAllLoansUseCase
) : ViewModel() {

    private val resources = context.resources

    private val workManager = WorkManager.getInstance()

    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private lateinit var future: ScheduledFuture<*>

    companion object {
        const val EXECUTOR_DELAY = 0L
        const val EXECUTOR_PERIOD = 10L
        const val REPEAT_INTERVAL = 3L
        const val INITIAL_DELAY = 10L
    }

    private val _enableDarkModeEvent = SingleLiveEvent<Boolean>()
    val enableDarkModeEvent: LiveData<Boolean> = _enableDarkModeEvent

    private val _existsApprovedLoans = MutableLiveData<Boolean>()

    fun checkExistsApprovedLoans() {
        future = executorService.scheduleAtFixedRate({
            viewModelScope.launch {
                val response = getAllLoansUseCase()
                if (response is DataStatus.Success) {
                    val loans = response.data
                    val approvedLoansExists = loans?.any {
                        it.state == resources.getString(R.string.approved)
                    }
                    approvedLoansExists?.let {
                        _existsApprovedLoans.value = it
                    }
                }
            }
        }, EXECUTOR_DELAY, EXECUTOR_PERIOD, TimeUnit.SECONDS)
    }

    private fun disableCheckApprovedLoans() {
        future.cancel(true)
    }

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

    fun onViewResume() {
        workManager.cancelAllWork()
    }

    fun onViewStop() {
        disableCheckApprovedLoans()
        _existsApprovedLoans.value?.let {
            if (it) {
                initWorker()
            }
        }
    }

    private fun initWorker() {
        val work =
            PeriodicWorkRequestBuilder<LoansNotificationWorker>(REPEAT_INTERVAL, TimeUnit.HOURS)
                .setInitialDelay(INITIAL_DELAY, TimeUnit.SECONDS)
                .build()

        workManager.enqueue(work)
    }
}