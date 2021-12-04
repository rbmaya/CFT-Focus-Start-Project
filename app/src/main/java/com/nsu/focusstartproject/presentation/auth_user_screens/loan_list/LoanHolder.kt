package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.LoanListItemBinding
import com.nsu.focusstartproject.domain.Loan

class LoanHolder(
    private val binding: LoanListItemBinding,
    private val onClick: (Long) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    private val resources = binding.root.context.resources

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
            val drawable = when (loan.state) {
                resources.getString(R.string.approved) -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_approved, null)
                }
                resources.getString(R.string.rejected) -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_rejected, null)
                }
                else -> ResourcesCompat.getDrawable(resources, R.drawable.ic_in_progress, null)
            }
            state.setImageDrawable(drawable)
        }
    }
}