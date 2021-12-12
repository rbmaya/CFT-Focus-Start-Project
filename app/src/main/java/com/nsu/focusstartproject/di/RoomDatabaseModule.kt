package com.nsu.focusstartproject.di

import android.content.Context
import androidx.room.Room
import com.nsu.focusstartproject.data.loans_cache.SavedLoanDao
import com.nsu.focusstartproject.data.loans_cache.SavedLoanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext applicationContext: Context): SavedLoanDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SavedLoanDatabase::class.java, "saved_loan_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSavedLoanDao(savedLoanDatabase: SavedLoanDatabase): SavedLoanDao {
        return savedLoanDatabase.getSavedLoanDao()
    }
}