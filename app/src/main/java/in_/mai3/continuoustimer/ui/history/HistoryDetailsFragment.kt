package in_.mai3.continuoustimer.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import in_.mai3.continuoustimer.databinding.FragmentHistoryBinding
import in_.mai3.continuoustimer.databinding.FragmentHistoryDetailsBinding
import in_.mai3.continuoustimer.ui.base.BaseFragment
import in_.mai3.continuoustimer.ui.history.adapter.HistoryAdapter
import in_.mai3.continuoustimer.ui.history.adapter.HistoryDetailsAdapter
import in_.mai3.continuoustimer.utils.logE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HistoryDetailsFragment : BaseFragment<FragmentHistoryDetailsBinding>(FragmentHistoryDetailsBinding::inflate) {

    private val historyViewModel by viewModels<HistoryViewModel>()
    private val args: HistoryDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.historyId = args.historyId
        historyViewModel.scheduleId = args.scheduleId

        binding.recViewHistoryDetails.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launchWhenCreated {
            historyViewModel.getHistoryDetails().collectLatest {
                it.toString().logE()
                binding.recViewHistoryDetails.adapter = HistoryDetailsAdapter(it) { which, historyDetailsTask ->


                }
            }
        }
    }


}