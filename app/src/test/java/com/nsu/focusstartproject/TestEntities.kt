package com.nsu.focusstartproject

import com.nsu.focusstartproject.data.auth.UserDto
import com.nsu.focusstartproject.data.loans_cache.SavedLoan
import com.nsu.focusstartproject.data.loans_cache.toSavedLoan
import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import com.nsu.focusstartproject.domain.UserInfo

object TestEntities {
    //Preferences
    val IS_FIRST_ENTER: Boolean = false

    val TOKEN: String = "dkssjdshjaldkdldkjdksldkl"

    val IS_DARK_MODE: Boolean = true

    //Auth data

    val USER_INFO: UserInfo = UserInfo(
        name = "Ivan",
        password = "password"
    )

    val USER_DTO = UserDto(name = "Ivan", role = "ADMIN")

    val NO_VALID_USER_INFO: UserInfo = UserInfo(
        name = "",
        password = "password2"
    )

    //Loans

    val LOAN_ID: Long = 30

    val LOAN_1: Loan = Loan(
        id = LOAN_ID,
        amount = 112121,
        date = "13.12.2000",
        firstName = "Ivan",
        lastName = "Ivanov",
        percent = 13.2,
        period = 30,
        phoneNumber = "2121821892",
        state = "APPROVED"
    )

    val LOAN_2: Loan = Loan(
        id = 21,
        amount = 2192012,
        date = "04.05.2020",
        firstName = "Petr",
        lastName = "Petrov",
        percent = 5.3,
        period = 45,
        phoneNumber = "2172918291",
        state = "REJECTED"
    )

    val LOAN_3: Loan = Loan(
        id = 14,
        amount = 12098,
        date = "25.06.2018",
        firstName = "Pavel",
        lastName = "Pavlov",
        percent = 12.89,
        period = 75,
        phoneNumber = "21212",
        state = "REGISTERED"
    )

    val LOANS: List<Loan> = listOf(
        LOAN_1, LOAN_2, LOAN_3
    )

    val SAVED_LOANS: List<SavedLoan> = listOf(
        LOAN_1.toSavedLoan(), LOAN_2.toSavedLoan(), LOAN_3.toSavedLoan()
    )

    val LOAN_REQUEST = LoanRequest(
        amount = 12098,
        firstName = "Pavel",
        lastName = "Pavlov",
        percent = 3.5,
        period = 40,
        phoneNumber = "21212",
    )

    val LOAN_CONDITION = LoanCondition(
        maxAmount = 15000,
        percent = 3.5,
        period = 40
    )
}