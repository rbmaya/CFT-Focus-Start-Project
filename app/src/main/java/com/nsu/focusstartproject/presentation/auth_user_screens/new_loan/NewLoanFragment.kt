package com.nsu.focusstartproject.presentation.auth_user_screens.new_loan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.NewLoanFragmentBinding
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import com.nsu.focusstartproject.utils.FieldsError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewLoanFragment : Fragment(R.layout.new_loan_fragment) {

    private val viewModel: NewLoanViewModel by viewModels()
    private val binding: NewLoanFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initLoanConditions()

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            amount.addTextChangedListener {
                amountLayout.error = null
            }
            addLoan.setOnClickListener {
                viewModel.createLoanRequest(
                    amount = Integer.parseInt(amount.text.toString()),
                    firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    phoneNumber = phoneNumber.text.toString()
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.loanConditionsStatus.observe(viewLifecycleOwner) {
            processLoanConditions(it)
        }
        viewModel.wrongFieldsEvent.observe(viewLifecycleOwner) {
            processFieldsError(it)
        }
        viewModel.loanRequestStatus.observe(viewLifecycleOwner) {
            processData(it)
        }
        viewModel.navigateToLoanList.observe(viewLifecycleOwner) {
            navigateToLoanList()
        }
    }

    private fun processData(dataStatus: DataStatus<Loan>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.newLoanProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.newLoanProgressBar.visibility = View.INVISIBLE
                viewModel.onSuccessLoanRequest()
            }
            is DataStatus.Error -> {
                binding.newLoanProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let {
                    processErrorCode(
                        errorCode = it,
                        message = getString(R.string.error_creating_loan)
                    )
                }
            }
        }
    }

    private fun processLoanConditions(dataStatus: DataStatus<LoanCondition>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.newLoanProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.newLoanProgressBar.visibility = View.INVISIBLE
                dataStatus.data?.let {
                    fillFields(it)
                }
            }
            is DataStatus.Error -> {
                binding.newLoanProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let {
                    processErrorCode(
                        errorCode = it,
                        message = getString(R.string.error_loading_conditions)
                    )
                }
                viewModel.onErrorLoadingConditions()
            }
        }
    }

    private fun processFieldsError(fieldsError: FieldsError) {
        when (fieldsError) {
            FieldsError.LARGE_AMOUNT -> {
                binding.amountLayout.error = binding.amountLayout.helperText
            }
            else -> {}
        }
    }

    private fun fillFields(loanCondition: LoanCondition) {
        binding.apply {
            val period = loanCondition.period.toString()
            val percent = loanCondition.percent.toString()
            conditions.text = getString(R.string.conditions, percent, period)
            val maxAmount = loanCondition.maxAmount.toString()
            amountLayout.helperText = getString(R.string.amount_helper_text, maxAmount)
        }
    }

    private fun processErrorCode(errorCode: ErrorCode, message: String) {
        when (errorCode) {
            ErrorCode.UNAUTHORIZED -> {
                showMessage("$message ${getString(R.string.unauthorized_error_body)}")
            }
            ErrorCode.FORBIDDEN -> {
                showMessage("$message ${getString(R.string.forbidden_error_body)}")
            }
            ErrorCode.NOT_FOUND -> {
                showMessage("$message ${getString(R.string.not_found_error_body)}")
            }
            ErrorCode.UNKNOWN -> {
                showMessage("$message ${getString(R.string.unknown_error_body)}")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToLoanList() {
        findNavController().navigateUp()
    }


}