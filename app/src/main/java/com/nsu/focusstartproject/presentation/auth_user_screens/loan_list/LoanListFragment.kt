package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListFragmentBinding
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

    }

}