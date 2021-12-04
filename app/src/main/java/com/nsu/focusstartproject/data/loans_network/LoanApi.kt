package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import com.nsu.focusstartproject.domain.LoanCondition
import com.nsu.focusstartproject.domain.LoanRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoanApi {

    @GET("/loans/{id}")
    suspend fun getLoan(@Path("id") id: Long): Response<Loan>

    @GET("/loans/all")
    suspend fun getAllLoans(): Response<List<Loan>>

    @POST("/loans")
    suspend fun createLoanRequest(@Body loanRequest: LoanRequest): Response<Loan>

    @GET("/loans/conditions")
    suspend fun getLoanConditions(): Response<LoanCondition>

}