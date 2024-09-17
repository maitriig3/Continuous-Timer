package in_.mai3.continuoustimer.data.local.room.repository

import in_.mai3.continuoustimer.data.local.room.DatabaseC
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.HistoryDetails
import in_.mai3.continuoustimer.data.local.room.entity.Schedule
import in_.mai3.continuoustimer.data.local.room.entity.Tasks
import in_.mai3.continuoustimer.utils.getTimeInMillis

class RoomRepository(private val databaseC: DatabaseC) {

    suspend fun insertSchedule(schedule: String,breakInBetween: String){
        databaseC.scheduleDao().insert(Schedule(0,schedule,breakInBetween, getTimeInMillis(), getTimeInMillis()))
    }

    suspend fun getScheduleId() = databaseC.scheduleDao().getScheduleInt()

    fun getAllSchedule() = databaseC.scheduleDao().getAll()

    suspend fun deleteSchedule(scheduleId: Int) = databaseC.scheduleDao().delete(scheduleId)

    suspend fun getSchedule(scheduleId: Int) = databaseC.scheduleDao().getSchedule(scheduleId)

    suspend fun insertTasks(scheduleId: Int, task: String, time: String, timeUnit: String){
        databaseC.tasksDao().insert(Tasks(0, scheduleId,task,time,timeUnit, getTimeInMillis(),
            getTimeInMillis()
        ))
    }

    fun getAllTasks(scheduleId: Int) = databaseC.tasksDao().getAll(scheduleId)

    suspend fun getAllTasksService(scheduleId: Int) = databaseC.tasksDao().getAllService(scheduleId)

    suspend fun deleteTask(tasksId: Int) = databaseC.tasksDao().delete(tasksId)

    suspend fun getTask(tasksId: Int) = databaseC.tasksDao().getTask(tasksId)

    suspend fun updateTask(task: String,time: String,timeUnit: String,tasksId: Int) = databaseC.tasksDao().updateTask(task,time,timeUnit,
        getTimeInMillis(),tasksId
    )

    suspend fun insertHistory(scheduleId: Int) = databaseC.historyDao().insert(
        History(0,scheduleId, databaseC.scheduleDao().getSchedule(scheduleId).schedule,
            getTimeInMillis(),0
        )
    )

    suspend fun updateHistory(history: History) {
        databaseC.historyDao().update(
            History(history.historyId,history.scheduleId,history.schedule,history.startedTime,
                getTimeInMillis()
            )
        )
    }

    suspend fun getRecentHistory() = databaseC.historyDao().getRecent()
    private suspend fun getRecentHistoryDetails() = databaseC.historyDetailsDao().getRecent()

    suspend fun insertHistoryDetails(historyId: Int,scheduleId: Int,taskId: Int,acknowledged: Boolean) = databaseC.historyDetailsDao().insert(
        HistoryDetails(
        0, historyId, scheduleId, taskId, databaseC.tasksDao().getTask(taskId).task, acknowledged, getTimeInMillis(), getTimeInMillis()
        )
    )

    suspend fun updateHistoryDetails(){
        val historyDetails = getRecentHistoryDetails()
        databaseC.historyDetailsDao().update(
            HistoryDetails(
                historyDetails.historyDetailId,historyDetails.historyId,historyDetails.scheduleId,historyDetails.taskId, historyDetails.task,
                true,historyDetails.addedTime,
                getTimeInMillis()
            )
        )
    }

    fun getHistory() = databaseC.historyDao().getAll()

    fun getHistoryDetails(historyId: Int) = databaseC.historyDetailsDao().getAll(historyId)


}