package in_.mai3.continuoustimer.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import in_.mai3.continuoustimer.data.local.room.dao.HistoryDao
import in_.mai3.continuoustimer.data.local.room.dao.HistoryDetailsDao
import in_.mai3.continuoustimer.data.local.room.dao.ScheduleDao
import in_.mai3.continuoustimer.data.local.room.dao.TasksDao
import in_.mai3.continuoustimer.data.local.room.dao.TimeTDao
import in_.mai3.continuoustimer.data.local.room.dao.TimeTtaskDao
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.HistoryDetails
import in_.mai3.continuoustimer.data.local.room.entity.Schedule
import in_.mai3.continuoustimer.data.local.room.entity.Tasks
import in_.mai3.continuoustimer.data.local.room.entity.TimeTData
import in_.mai3.continuoustimer.data.local.room.entity.TimeTtaskData

@Database(entities = [TimeTData::class, TimeTtaskData::class,Schedule::class,Tasks::class,History::class,HistoryDetails::class], version = 1)
abstract class DatabaseC : RoomDatabase() {

    companion object{
        @Volatile
        var databaseC : DatabaseC? = null

        fun getDatabase(context : Context) : DatabaseC {
            if(databaseC == null){
                synchronized(this){
                    databaseC = Room.databaseBuilder(context.applicationContext, DatabaseC::class.java,
                        "timerDB").build()
                }
            }
            return databaseC!!
        }
    }

    abstract fun timeTDao() : TimeTDao
    abstract fun timeTtaskDao() : TimeTtaskDao
    abstract fun scheduleDao() : ScheduleDao
    abstract fun tasksDao() : TasksDao
    abstract fun historyDao() : HistoryDao
    abstract fun historyDetailsDao() : HistoryDetailsDao

}