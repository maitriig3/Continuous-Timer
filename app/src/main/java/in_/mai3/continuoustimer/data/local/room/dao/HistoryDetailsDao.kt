package in_.mai3.continuoustimer.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import in_.mai3.continuoustimer.data.local.room.HistoryDetailsTask
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.HistoryDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDetailsDao {

    @Insert
    suspend fun insert(historyDetails: HistoryDetails)

    @Update
    suspend fun update(historyDetails: HistoryDetails)

    @Query("select * from history_details_table where history_id =:historyId")
    fun getAll(historyId: Int): Flow<List<HistoryDetailsTask>>



    @Query("select * from history_details_table order by added_time desc limit 1")
    suspend fun getRecent(): HistoryDetails
}