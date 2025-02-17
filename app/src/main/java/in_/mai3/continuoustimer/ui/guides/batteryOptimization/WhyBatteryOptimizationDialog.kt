package in_.mai3.continuoustimer.ui.guides.batteryOptimization

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import in_.mai3.continuoustimer.databinding.DialogWhyBatteryOptimizationBinding
import in_.mai3.continuoustimer.ui.SettingsViewModel
import in_.mai3.continuoustimer.ui.guides.GuideInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhyBatteryOptimizationDialog: DialogFragment() {

    private lateinit var guideInterface: GuideInterface
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        guideInterface = requireParentFragment().childFragmentManager.fragments[0] as GuideInterface
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogWhyBatteryOptimizationBinding.inflate(layoutInflater)

        binding.btnPositive.setOnClickListener {
            settingsViewModel.setBatteryOptimization(binding.checkBoxDontAskAgain.isChecked)
            guideInterface.whyBatteryOptimization(true)
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            settingsViewModel.setBatteryOptimization(binding.checkBoxDontAskAgain.isChecked)
            guideInterface.whyBatteryOptimization(false)
            dismiss()
        }


        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }

}