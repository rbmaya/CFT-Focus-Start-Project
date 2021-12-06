package com.nsu.focusstartproject.presentation.auth_user_screens.loan_details

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.GetLoanDialogBinding
import com.nsu.focusstartproject.databinding.LoanDetailsFragmentBinding
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoanDetailsFragment : Fragment(R.layout.loan_details_fragment) {
    private val viewModel: LoanDetailsViewModel by viewModels()
    private val binding: LoanDetailsFragmentBinding by viewBinding()
    private val navArgs: LoanDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadLoanDetails(navArgs.id)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.loanDetailsStatus.observe(viewLifecycleOwner) {
            processData(it)
        }
        viewModel.showGetLoanInfo.observe(viewLifecycleOwner) {
            showGetLoanInfo()
        }
    }

    private fun initListeners() {
        binding.howGetLoan.setOnClickListener {
            showGetLoanAlertDialog()
        }
    }

    private fun showGetLoanAlertDialog() {
        val view: View = GetLoanDialogBinding.inflate(layoutInflater).root
        AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun showGetLoanInfo() {
        binding.howGetLoan.visibility = View.VISIBLE
    }


    private fun processData(dataStatus: DataStatus<Loan>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.loanProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.loanProgressBar.visibility = View.INVISIBLE
                dataStatus.data?.let {
                    if (it.state == getString(R.string.approved)) {
                        viewModel.onApprovedLoan()
                    }
                    val formattedLoan = viewModel.formatLoan(it)
                    fillFields(formattedLoan)
                }
            }
            is DataStatus.Error -> {
                binding.loanProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let {
                    processErrorCode(
                        errorCode = it,
                        message = getString(R.string.error_loading_loan)
                    )
                }
            }
        }
    }

    private fun fillFields(loan: Loan) {
        binding.apply {
            amount.text = loan.amount.toString()
            firstName.text = loan.firstName
            lastName.text = loan.lastName
            percent.text = getString(R.string.percent_formatted, loan.percent.toString())
            period.text = getString(R.string.period_formatted, loan.period.toString())
            phoneNumber.text = loan.phoneNumber
            fillState(loan)
            date.text = loan.date
        }
    }

    private fun fillState(loan: Loan) {
        binding.apply {
            state.text = loan.state
            val color = when (loan.state) {
                resources.getString(R.string.approved) -> {
                    ResourcesCompat.getColor(resources, R.color.green, null)
                }
                resources.getString(R.string.rejected) -> {
                    ResourcesCompat.getColor(resources, R.color.red, null)
                }
                else -> ResourcesCompat.getColor(resources, R.color.black, null)
            }
            state.setTextColor(color)
        }
    }

    private fun processErrorCode(errorCode: ErrorCode, message: String) {
        when (errorCode) {
            ErrorCode.UNAUTHORIZED -> {
                showMessage("${getString(R.string.unauthorized_error_body)} $message")
            }
            ErrorCode.FORBIDDEN -> {
                showMessage("${getString(R.string.forbidden_error_body)} $message")
            }
            ErrorCode.NOT_FOUND -> {
                showMessage("${getString(R.string.not_found_error_body)} $message")
            }
            ErrorCode.UNKNOWN -> {
                showMessage("${getString(R.string.unknown_error_body)} $message")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


}