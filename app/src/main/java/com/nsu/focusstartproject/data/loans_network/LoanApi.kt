package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.Loan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LoanApi {

    @GET("/loans{id}")
    suspend fun getLoan(@Path("id") id: Int): Response<Loan>

    @GET("/loans/all")
    suspend fun getAllLoans(): Response<List<Loan>>

}