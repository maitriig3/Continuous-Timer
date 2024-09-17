package in_.mai3.continuoustimer.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import in_.mai3.continuoustimer.databinding.FragmentHistoryBinding
import in_.mai3.continuoustimer.ui.base.BaseFragment
import in_.mai3.continuoustimer.ui.history.adapter.HistoryAdapter
import in_.mai3.continuoustimer.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recViewHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launchWhenCreated {
//            historyViewModel.getHistory().collectLatest {
//                binding.recViewHistory.adapter = HistoryAdapter(it) { which, historySchedule ->
//                    navigate(HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailsFragment(historySchedule.historyId,historySchedule.scheduleId))
//                }
//            }
        }
    }


}