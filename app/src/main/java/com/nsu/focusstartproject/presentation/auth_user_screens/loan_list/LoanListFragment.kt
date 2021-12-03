package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListFragmentBinding
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanListFragment : Fragment(R.layout.loan_list_fragment) {

    private val viewModel: LoanListViewModel by viewModels()
    private val binding: LoanListFragmentBinding by viewBinding()

    private val loanListAdapter = LoanListAdapter {
        //TODO navigate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loanList.adapter = loanListAdapter
        viewModel.loadData()

        initObservers()
    }

    private fun initObservers(){
        viewModel.loadDataState.observe(viewLifecycleOwner){
            processData(it)
        }
    }

    private fun processData(dataStatus: DataStatus<List<Loan>>){
        when (dataStatus){
            is DataStatus.Loading -> {
                binding.loansProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.loansProgressBar.visibility = View.INVISIBLE
                val loans = dataStatus.data
                loans?.let { loanListAdapter.loans = loans }
            }
            is DataStatus.Error -> {
                binding.loansProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let { processErrorCode(it) }
            }
        }
    }

    private fun processErrorCode(errorCode: ErrorCode){
        when (errorCode){
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

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}