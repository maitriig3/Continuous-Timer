package in_.mai3.continuoustimer.ui.history

import androidx.lifecycle.ViewModel
import in_.mai3.continuoustimer.data.local.room.HistoryDetailsTask
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.HistoryDetails
import in_.mai3.continuoustimer.data.local.room.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val roomRepository: RoomRepository): ViewModel(){

    var dateFilter = ""
    var addedTimeFilter = false
    var ascFilter = false

    var scheduleId = -1
    var historyId = -1

    fun getHistory() = flow {
        roomRepository.getHistory().collect{ historyList ->
            if(dateFilter.isEmpty()){
                if(ascFilter){
                    emit(historyList.sortedBy { if(addedTimeFilter) it.startedTime else it.endedTime })
                }else{
                    emit(historyList.sortedByDescending { if(addedTimeFilter) it.startedTime else it.endedTime })
                }
            }
        }
    }


    fun getHistoryDetails() = flow<List<HistoryDetailsTask>> {
//        roomRepository.getHistoryDetails(scheduleId,historyId).collect{ historyDetailsList ->
//            if(dateFilter.isEmpty()){
//                if(ascFilter){
//                    emit(historyDetailsList.sortedBy { it.addedTime})
//                }else{
//                    emit(historyDetailsList.sortedByDescending {it.addedTime })
//                }
//            }
//        }
    }

}