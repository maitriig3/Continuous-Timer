package in_.mai3.continuoustimer.utils

import androidx.lifecycle.MutableLiveData
import in_.mai3.continuoustimer.data.local.room.TimerUpdate

object LiveDataTimer {

    var timerUpdate = MutableLiveData<TimerUpdate>()

}