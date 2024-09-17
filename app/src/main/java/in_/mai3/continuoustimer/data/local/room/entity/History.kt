package in_.mai3.continuoustimer.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("history_id")
    val historyId: Int,
    @ColumnInfo("schedule_id")
    val scheduleId: Int,
    @ColumnInfo("schedule")
    val schedule: String,
    @ColumnInfo("started_time")
    val startedTime: Long,
    @ColumnInfo("ended_time")
    val endedTime: Long

)
