package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.auth.AuthApi
import com.nsu.focusstartproject.data.loans_network.LoanApi
import com.nsu.focusstartproject.data.loans_network.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    @AuthApiQualifier
    fun provideAuthRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    }).readTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://focusstart.appspot.com/")
            .build()

    @Singleton
    @Provides
    @LoanApiQualifier
    fun provideLoanRetrofit(tokenInterceptor: TokenInterceptor): Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    }).readTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(tokenInterceptor)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://focusstart.appspot.com/")
            .build()

    @Singleton
    @Provides
    @AuthApiQualifier
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    @LoanApiQualifier
    fun provideLoanApi(retrofit: Retrofit): LoanApi = retrofit.create(LoanApi::class.java)
}