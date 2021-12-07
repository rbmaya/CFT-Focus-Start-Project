package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListFragmentBinding
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.errors_processing.ErrorCodeProcessor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoanListFragment : Fragment(R.layout.loan_list_fragment) {

    private val viewModel: LoanListViewModel by viewModels()
    private val binding: LoanListFragmentBinding by viewBinding()

    @Inject
    lateinit var errorCodeProcessor: ErrorCodeProcessor

    private val loanListAdapter = LoanListAdapter {
        viewModel.onLoanClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        viewModel.loadData()
    }

    private fun initViews() {
        binding.loanList.adapter = loanListAdapter
        binding.apply {
            addLoanButton.setOnClickListener {
                viewModel.onAddLoanButtonClicked()
            }
            swipeToRefreshLayout.setOnRefreshListener {
                viewModel.loadData()
            }
        }
    }

    private fun initObservers() {
        viewModel.loadDataState.observe(viewLifecycleOwner) {
            processData(it)
        }
        viewModel.navigateToNewLoan.observe(viewLifecycleOwner) {
            navigateToNewLoanFragment()
        }
        viewModel.navigateToLoanDetails.observe(viewLifecycleOwner) {
            navigateToLoanDetailsFragment(it)
        }
    }


    private fun processData(dataStatus: DataStatus<List<Loan>>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.swipeToRefreshLayout.isRefreshing = true
            }
            is DataStatus.Success -> {
                binding.swipeToRefreshLayout.isRefreshing = false
                val loans = dataStatus.data
                loans?.let {
                    loanListAdapter.loans = viewModel.processLoans(loans)
                }
            }
            is DataStatus.Error -> {
                binding.swipeToRefreshLayout.isRefreshing = false
                dataStatus.code?.let {
                    val message = errorCodeProcessor.processErrorCode(it)
                    showMessage(message)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToNewLoanFragment() {
        findNavController().navigate(R.id.action_loanListFragment_to_newLoanFragment)
    }

    private fun navigateToLoanDetailsFragment(id: Long) {
        val action = LoanListFragmentDirections.actionLoanListFragmentToLoanDetailsFragment(id)
        findNavController().navigate(action)
    }
}