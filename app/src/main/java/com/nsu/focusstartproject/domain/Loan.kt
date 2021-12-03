package com.nsu.focusstartproject.domain

data class Loan(
    val id: Int,
    val amount: Int,
    val date: String,
    val firstName: String,
    val secondName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
)