package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListItemBinding
import com.nsu.focusstartproject.domain.Loan

class LoanHolder(
    private val binding: LoanListItemBinding,
    private val onClick: (Long) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    private val resources: Context = binding.root.context

    companion object {
        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            onClick: (Long) -> Unit
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
            amount.text = resources.getString(R.string.money, loan.amount.toString())
            fillState(loan)

            val periodText = resources.getString(R.string.period, loan.period.toString())
            period.text = periodText

            root.setOnClickListener {
                onClick(loan.id)
            }
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
}