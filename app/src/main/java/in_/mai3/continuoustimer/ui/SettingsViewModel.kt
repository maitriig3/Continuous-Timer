package in_.mai3.continuoustimer.ui

import androidx.lifecycle.ViewModel
import in_.mai3.continuoustimer.data.local.room.repository.PreferencesRepository
import in_.mai3.continuoustimer.utils.Constants.CANNOT_ASK_ALLOW_NOTIFICATIONS
import in_.mai3.continuoustimer.utils.Constants.CANNOT_ASK_BATTERY_OPTIMIZATION
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesRepository: PreferencesRepository) : ViewModel() {


    fun setBatteryOptimization(value: Boolean) = preferencesRepository.setBoolean(CANNOT_ASK_BATTERY_OPTIMIZATION,value)

    fun setAllowNotifications(value: Boolean) = preferencesRepository.setBoolean(
        CANNOT_ASK_ALLOW_NOTIFICATIONS,value)

    fun getBatteryOptimization() = preferencesRepository.getBoolean(CANNOT_ASK_BATTERY_OPTIMIZATION)

    fun getAllowNotifications() = preferencesRepository.getBoolean(CANNOT_ASK_ALLOW_NOTIFICATIONS)

}