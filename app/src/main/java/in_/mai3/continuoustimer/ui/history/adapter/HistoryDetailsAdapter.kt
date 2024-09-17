package in_.mai3.continuoustimer.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import in_.mai3.continuoustimer.R
import in_.mai3.continuoustimer.data.local.room.HistoryDetailsTask
import in_.mai3.continuoustimer.data.local.room.entity.History
import in_.mai3.continuoustimer.data.local.room.entity.HistoryDetails
import in_.mai3.continuoustimer.data.local.room.entity.Schedule
import in_.mai3.continuoustimer.databinding.AdapterHistoryBinding
import in_.mai3.continuoustimer.databinding.AdapterHistoryDetailsBinding
import in_.mai3.continuoustimer.databinding.AdapterScheduleBinding
import in_.mai3.continuoustimer.ui.schedule.adapter.ScheduleAdapter
import in_.mai3.continuoustimer.utils.Constants
import in_.mai3.continuoustimer.utils.getDate

class HistoryDetailsAdapter(var historyDetailsTask: List<HistoryDetailsTask>, val onClick: (Int, HistoryDetailsTask) -> Unit) :
    RecyclerView.Adapter<HistoryDetailsAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: AdapterHistoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AdapterHistoryDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            txtTask.text = historyDetailsTask[position].task
            if(historyDetailsTask[position].acknowledged == null){
                imgApp.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_timer_dead,null
                ))
                imgUser.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_user_side_eye_left,null
                ))
            }else{
                historyDetailsTask[position].acknowledged?.let {
                    if(it){
                        imgApp.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_timer_alive,null
                        ))
                        imgUser.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_user_alive,null
                        ))
                    }else{
                        imgApp.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_timer_side_eye_right,null
                        ))
                        imgUser.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.ic_user_dead,null
                        ))
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = historyDetailsTask.size
}