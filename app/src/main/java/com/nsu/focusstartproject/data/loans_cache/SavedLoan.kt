package com.nsu.focusstartproject.data.loans_cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nsu.focusstartproject.domain.Loan

@Entity
data class SavedLoan(
    @PrimaryKey
    val id: Long,
    val amount: Int,
    val date: String,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
)

fun SavedLoan.toLoan(): Loan {
    return Loan(
        id = this.id,
        amount = this.amount,
        date = this.date,
        firstName = this.firstName,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phoneNumber = this.phoneNumber,
        state = this.state
    )
}

fun Loan.toSavedLoan(): SavedLoan {
    return SavedLoan(
        id = this.id,
        amount = this.amount,
        date = this.date,
        firstName = this.firstName,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phoneNumber = this.phoneNumber,
        state = this.state
    )
}