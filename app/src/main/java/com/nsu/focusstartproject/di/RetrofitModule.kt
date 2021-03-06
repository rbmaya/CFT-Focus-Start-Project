package com.nsu.focusstartproject.di

import com.nsu.focusstartproject.data.auth.AuthApi
import com.nsu.focusstartproject.data.loans_network.LoanApi
import com.nsu.focusstartproject.data.loans_network.NoConnectionInterceptor
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    @AuthApiQualifier
    fun provideAuthRetrofit(noConnectionInterceptor: NoConnectionInterceptor): Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor(noConnectionInterceptor)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://focusstart.appspot.com/")
            .build()

    @Singleton
    @Provides
    @LoanApiQualifier
    fun provideLoanRetrofit(
        noConnectionInterceptor: NoConnectionInterceptor,
        tokenInterceptor: TokenInterceptor
    ): Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor(noConnectionInterceptor)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(tokenInterceptor)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://focusstart.appspot.com/")
            .build()

    @Singleton
    @Provides
    fun provideAuthApi(@AuthApiQualifier retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideLoanApi(@LoanApiQualifier retrofit: Retrofit): LoanApi =
        retrofit.create(LoanApi::class.java)
}