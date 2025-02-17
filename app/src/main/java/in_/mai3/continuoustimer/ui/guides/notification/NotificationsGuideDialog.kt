package in_.mai3.continuoustimer.ui.guides.notification

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import in_.mai3.continuoustimer.databinding.DialogNotificationsGuideBinding
import in_.mai3.continuoustimer.ui.guides.GuideInterface
import in_.mai3.continuoustimer.ui.guides.batteryOptimization.WhyBatteryOptimizationDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsGuideDialog: DialogFragment() {

    private lateinit var guideInterface: GuideInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        guideInterface = requireParentFragment().childFragmentManager.fragments[0] as GuideInterface
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogNotificationsGuideBinding.inflate(layoutInflater)

        binding.btnPositive.setOnClickListener {
            guideInterface.notificationGuide(true)
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            guideInterface.notificationGuide(false)
            dismiss()
        }


        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }



}