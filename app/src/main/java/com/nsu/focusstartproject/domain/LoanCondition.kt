package com.nsu.focusstartproject.domain

data class LoanCondition(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)