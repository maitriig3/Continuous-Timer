package in_.mai3.continuoustimer.ui.schedule

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import in_.mai3.continuoustimer.R
import in_.mai3.continuoustimer.data.local.room.TimerUpdate
import in_.mai3.continuoustimer.databinding.FragmentScheduleBinding
import in_.mai3.continuoustimer.others.Constants
import in_.mai3.continuoustimer.ui.SettingsViewModel
import in_.mai3.continuoustimer.ui.base.BaseFragment
import in_.mai3.continuoustimer.ui.countdown.CountDownService
import in_.mai3.continuoustimer.ui.guides.GuideInterface
import in_.mai3.continuoustimer.ui.guides.batteryOptimization.BatteryOptimizationGuideDialog
import in_.mai3.continuoustimer.ui.guides.batteryOptimization.WhyBatteryOptimizationDialog
import in_.mai3.continuoustimer.ui.guides.notification.NotificationsGuideDialog
import in_.mai3.continuoustimer.ui.guides.notification.WhyAllowNotificationsDialog
import in_.mai3.continuoustimer.ui.schedule.adapter.ScheduleAdapter
import in_.mai3.continuoustimer.utils.BatteryOptimization
import in_.mai3.continuoustimer.utils.LiveDataTimer
import in_.mai3.continuoustimer.utils.logE
import in_.mai3.continuoustimer.utils.navigate
import in_.mai3.continuoustimer.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate),
    GuideInterface {

    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()
    lateinit var observer: Observer<TimerUpdate>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        observer = Observer<TimerUpdate> {
            "LIVE DATA ${it.time}".logE()
            if (it != null) {
                val time = it.time
                if (it.end)
                    scheduleViewModel.serviceIsNotRunning = true

                binding.time.text = Constants.miltotime(time.toLong())
//                    val ms = String.format(Locale.US,
//                        "%02d:%02d:%02d", time / 3600,
//                        time % 3600 / 60, time % 60
//                    )
            }
        }
        askPrerequisites()

        binding.recViewSchedule.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.btnNewSchedule.setOnClickListener {
            navigate(ScheduleFragmentDirections.actionScheduleFragmentToTasksFragment(-1))
        }

        binding.imgHistory.setOnClickListener {
            navigate(ScheduleFragmentDirections.actionScheduleFragmentToHistoryFragment())
        }

        lifecycleScope.launchWhenCreated {
            scheduleViewModel.getAll().collectLatest {
                binding.recViewSchedule.adapter = ScheduleAdapter(it) { which, schedule ->
                    if (which == 0) {
                        navigate(
                            ScheduleFragmentDirections.actionScheduleFragmentToTasksFragment(
                                schedule.scheduleId
                            )
                        )
                    } else if (which == 1) {
                        if (scheduleViewModel.serviceIsNotRunning) {
                            Intent(requireContext(), CountDownService::class.java).apply {
                                putExtra("schedule_id", schedule.scheduleId)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    requireActivity().startForegroundService(this)
                                } else
                                    requireActivity().startService(this)
                                scheduleViewModel.serviceIsNotRunning = false
                            }
                        } else {
                            "Timer is running".toast(requireContext())
                        }
                    } else if (which == 2) {
                        lifecycleScope.launchWhenCreated {
                            scheduleViewModel.delete(schedule.scheduleId)
                        }
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }

    private fun askPrerequisites() {
        if (!BatteryOptimization(requireContext()).isBatteryOptimizationIgnored()) {
            if (!settingsViewModel.getBatteryOptimization())
                WhyBatteryOptimizationDialog().show(parentFragmentManager, "1")
            else
                callWhyAllowNotification()
        }else{
            callWhyAllowNotification()
        }

    }

    override fun onResume() {
        super.onResume()
        LiveDataTimer.timerUpdate.observe(requireActivity(), observer)
    }

    override fun onPause() {
        super.onPause()
        LiveDataTimer.timerUpdate.removeObserver(observer)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(getString(R.string.channel_id), name, importance).apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun callWhyAllowNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (!settingsViewModel.getAllowNotifications())
                    WhyAllowNotificationsDialog().show(parentFragmentManager, "3")
            }
        }
    }

    override fun whyBatteryOptimization(allowed: Boolean) {
        if (allowed)
            BatteryOptimizationGuideDialog().show(parentFragmentManager, "2")
        else
            callWhyAllowNotification()
    }

    override fun batteryOptimizationGuide(allowed: Boolean) {
        if (allowed) {
            BatteryOptimization(requireContext()).goToBatteryOptimizationSettings()
        }
        callWhyAllowNotification()

    }

    override fun whyAllowNotification(allowed: Boolean) {
        if (allowed)
            NotificationsGuideDialog().show(parentFragmentManager, "4")

    }

    override fun notificationGuide(allowed: Boolean) {
        if (allowed) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


}