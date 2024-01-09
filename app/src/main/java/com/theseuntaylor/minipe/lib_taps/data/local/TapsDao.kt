package com.theseuntaylor.minipe.lib_taps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess

@Dao
interface TapsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTaps(taps: List<TapsSuccess>)

    @Query("DELETE from taps")
    suspend fun deleteTaps()

    @Transaction
    suspend fun refreshTaps(taps: List<TapsSuccess>) {
        deleteTaps()
        addTaps(taps = taps)
    }
}