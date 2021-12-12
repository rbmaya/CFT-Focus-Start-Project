package com.nsu.focusstartproject.data.loans_cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedLoan::class], exportSchema = false, version = 1)
abstract class SavedLoanDatabase : RoomDatabase() {

    abstract fun getSavedLoanDao(): SavedLoanDao

}