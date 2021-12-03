package com.nsu.focusstartproject.presentation.auth_user_screens.new_loan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.NewLoanFragmentBinding
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode

class NewLoanFragment : Fragment(R.layout.new_loan_fragment) {

    private val viewModel: NewLoanViewModel by viewModels()
    private val binding: NewLoanFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initLoanConditions()

    }

    private fun initObservers() {
        viewModel.loanConditionsStatus.observe(viewLifecycleOwner) {
            processLoanConditions(it)
        }
        viewModel.loanRequestStatus.observe(viewLifecycleOwner) {

        }
    }

    private fun processLoanConditions(dataStatus: DataStatus<LoanCondition>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                //TODO
            }
            is DataStatus.Success -> {
                dataStatus.data?.let {
                    binding.period.setText(it.period.toString())
                    binding.percent.setText(it.percent.toString())
                    binding.amountLayout.helperText = it.maxAmount.toString()
                }
            }
            is DataStatus.Error -> {
                dataStatus.code?.let { processErrorCode(errorCode = it) }
            }
        }
    }

    private fun processErrorCode(errorCode: ErrorCode) {
        when (errorCode) {
            ErrorCode.UNAUTHORIZED -> {
                showMessage(getString(R.string.unauthorized_error_body))
            }
            ErrorCode.FORBIDDEN -> {
                showMessage(getString(R.string.forbidden_error_body))
            }
            ErrorCode.NOT_FOUND -> {
                showMessage(getString(R.string.not_found_error_body))
            }
            ErrorCode.UNKNOWN -> {
                showMessage(getString(R.string.unknown_error_body))
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


}