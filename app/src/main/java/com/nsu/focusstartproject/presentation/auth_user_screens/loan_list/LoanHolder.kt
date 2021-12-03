package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListItemBinding
import com.nsu.focusstartproject.domain.Loan

class LoanHolder(
    private val binding: LoanListItemBinding,
    private val onClick: () -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            onClick: () -> Unit
        ): LoanHolder {
            return LoanHolder(
                LoanListItemBinding.inflate(layoutInflater, parent, false),
                onClick = onClick
            )
        }
    }

    fun bind(loan: Loan){
        binding.apply {
            date.text = loan.date
            amount.text = loan.amount.toString()
            state.text = loan.state
            val periodText = binding.root.context.getString(R.string.period, loan.period.toString())
            period.text = periodText
        }
    }
}