package com.nsu.focusstartproject.domain

data class LoanRequest(
    val amount: Int,
    val firstName: String,
    val secondName: String,
    val percent: Int,
    val period: Int,
    val phoneNumber: String
)