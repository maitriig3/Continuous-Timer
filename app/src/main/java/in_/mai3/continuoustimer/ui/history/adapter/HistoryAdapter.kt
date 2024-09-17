package in_.mai3.continuoustimer.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import in_.mai3.continuoustimer.data.local.room.HistorySchedule
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.Schedule
import in_.mai3.continuoustimer.databinding.AdapterHistoryBinding
import in_.mai3.continuoustimer.databinding.AdapterScheduleBinding
import in_.mai3.continuoustimer.ui.schedule.adapter.ScheduleAdapter
import in_.mai3.continuoustimer.utils.Constants
import in_.mai3.continuoustimer.utils.getDate

class HistoryAdapter(var historySchedule: List<HistorySchedule>, val onClick: (Int, HistorySchedule) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: AdapterHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AdapterHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            txtTask.text = historySchedule[position].schedule
            txtStartedTime.text = getDate(historySchedule[position].startedTime,Constants.DATE_DD_MM_YYYY_HH_MM_SS)
            txtEndedTime.text = getDate(historySchedule[position].endedTime,Constants.DATE_DD_MM_YYYY_HH_MM_SS)
            layoutHistory.setOnClickListener {
                onClick(0,historySchedule[position])
            }
        }
    }

    override fun getItemCount(): Int = historySchedule.size
}