package in_.mai3.continuoustimer.ui.guides.notification

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import in_.mai3.continuoustimer.databinding.DialogWhyAllowNotificationsBinding
import in_.mai3.continuoustimer.ui.SettingsViewModel
import in_.mai3.continuoustimer.ui.guides.GuideInterface
import in_.mai3.continuoustimer.ui.guides.batteryOptimization.WhyBatteryOptimizationDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhyAllowNotificationsDialog : DialogFragment() {

    private lateinit var guideInterface: GuideInterface
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        guideInterface = requireParentFragment().childFragmentManager.fragments[0] as GuideInterface
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogWhyAllowNotificationsBinding.inflate(layoutInflater)

        binding.btnPositive.setOnClickListener {
            settingsViewModel.setAllowNotifications(binding.checkBoxDontAskAgain.isChecked)
            guideInterface.whyAllowNotification(true)
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            settingsViewModel.setAllowNotifications(binding.checkBoxDontAskAgain.isChecked)
            guideInterface.whyAllowNotification(false)
            dismiss()
        }


        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }

}