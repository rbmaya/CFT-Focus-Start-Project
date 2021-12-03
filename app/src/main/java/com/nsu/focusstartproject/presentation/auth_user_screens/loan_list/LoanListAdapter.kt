package com.nsu.focusstartproject.presentation.auth_user_screens.loan_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsu.focusstartproject.domain.Loan

class LoanListAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<LoanHolder>() {

    var loans: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LoanHolder(layoutInflater = layoutInflater, parent = parent, onClick = onClick)
    }

    override fun onBindViewHolder(holder: LoanHolder, position: Int) {
        holder.bind(loans[position])
    }

    override fun getItemCount(): Int {
        return loans.size
    }
}