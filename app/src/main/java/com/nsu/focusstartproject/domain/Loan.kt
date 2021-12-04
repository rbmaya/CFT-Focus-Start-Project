package com.nsu.focusstartproject.domain

data class Loan(
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