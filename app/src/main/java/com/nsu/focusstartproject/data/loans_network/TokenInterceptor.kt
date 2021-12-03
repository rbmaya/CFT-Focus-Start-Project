package com.nsu.focusstartproject.data.loans_network

import com.nsu.focusstartproject.domain.preferences.GetTokenUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : Interceptor {

    companion object {
        const val AUTH_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val token = async { getTokenUseCase()}
            val request = chain.request()
                .newBuilder()
                .addHeader(AUTH_HEADER, token.await())
                .build()
            chain.proceed(request)
        }
    }
}