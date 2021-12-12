package com.nsu.focusstartproject.data.loans_cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedLoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLoans(list: List<SavedLoan>)

    @Query("SELECT * FROM savedloan ORDER BY date DESC")
    suspend fun getSavedLoans(): List<SavedLoan>

    @Query("DELETE FROM savedloan")
    suspend fun deleteLoans()
}