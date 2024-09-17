package in_.mai3.continuoustimer.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import in_.mai3.continuoustimer.data.local.room.HistorySchedule
import in_.mai3.continuoustimer.data.local.room.entity.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(history: History)
    @Update
    suspend fun update(history: History)

    @Query("select * from history_table where ended_time =0 order by started_time desc limit 1")
    suspend fun getRecent(): History

    @Query("select * from history_table")
    fun getAll(): Flow<List<History>>
}