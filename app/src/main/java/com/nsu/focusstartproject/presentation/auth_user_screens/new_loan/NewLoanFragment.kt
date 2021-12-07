package com.nsu.focusstartproject.presentation.auth_user_screens.new_loan

import android.app.AlertDialog
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
import com.nsu.focusstartproject.databinding.SuccessLoanRequestDialogBinding
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.errors_processing.ErrorCodeProcessor
import com.nsu.focusstartproject.utils.fields_processing.FieldsError
import com.nsu.focusstartproject.utils.view_actions.Animations.wiggle
import com.nsu.focusstartproject.utils.view_actions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewLoanFragment : Fragment(R.layout.new_loan_fragment) {

    private val viewModel: NewLoanViewModel by viewModels()
    private val binding: NewLoanFragmentBinding by viewBinding()

    @Inject
    lateinit var errorCodeProcessor: ErrorCodeProcessor

    companion object {
        const val WIGGLE_FIELD_TIME = 500L
    }

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
                hideKeyboard()
                val amount: Int? = try {
                    Integer.parseInt(amount.text.toString())
                } catch (exc: Exception) {
                    null
                }
                viewModel.createLoanRequest(
                    amount = amount,
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
        viewModel.showSuccessfulRequest.observe(viewLifecycleOwner) {
            showAlertDialogSuccessfulRequest()
        }
        viewModel.navigateToLoanList.observe(viewLifecycleOwner) {
            navigateToLoanList()
        }
    }

    private fun showAlertDialogSuccessfulRequest() {
        val view: View = SuccessLoanRequestDialogBinding.inflate(layoutInflater).root
        AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
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
                    val message = errorCodeProcessor.processErrorCode(it)
                    showMessage(getString(R.string.error_creating_loan, message))
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
                    val message = errorCodeProcessor.processErrorCode(it)
                    showMessage(getString(R.string.error_loading_conditions, message))
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
            FieldsError.EMPTY_FIRST_NAME -> {
                binding.firstName.wiggle(WIGGLE_FIELD_TIME)
            }
            FieldsError.EMPTY_LAST_NAME -> {
                binding.lastName.wiggle(WIGGLE_FIELD_TIME)
            }
            FieldsError.EMPTY_PHONE_NUMBER -> {
                binding.phoneNumber.wiggle(WIGGLE_FIELD_TIME)
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

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToLoanList() {
        findNavController().navigateUp()
    }

}