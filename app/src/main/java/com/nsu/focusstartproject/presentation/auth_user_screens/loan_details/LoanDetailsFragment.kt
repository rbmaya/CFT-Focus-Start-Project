package com.nsu.focusstartproject.presentation.auth_user_screens.loan_details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
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
    }

    private fun initObservers() {
        viewModel.loanDetailsStatus.observe(viewLifecycleOwner) {
            processData(it)
        }
    }


    private fun processData(dataStatus: DataStatus<Loan>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.loanProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.loanProgressBar.visibility = View.INVISIBLE
                dataStatus.data?.let {
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
                    Color.GREEN
                }
                resources.getString(R.string.rejected) -> {
                    Color.RED
                }
                else -> Color.BLACK
            }
            state.setTextColor(color)
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


}