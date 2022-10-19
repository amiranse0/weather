package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.model.local.LocalHour
import com.example.weather.databinding.HoursCardViewBinding

class MainHoursAdapter : ListAdapter<LocalHour, MainHoursAdapter.HourViewHolder>(
    MainHourAdapterDiffCallback()
) {

    class HourViewHolder(
        val binding: HoursCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = HoursCardViewBinding.inflate(inflater)

        return HourViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.hourTimeTv.text = item.timeDate
        holder.binding.tempHourTv.text = holder.context.getString(
            R.string.temperature_template,
            item.temperature
        )
        holder.binding.rainProbabilityTv.text =
            holder.context.getString(R.string.chance_probability_template, item.chanceOfRain)
        holder.binding.snowProbabilityTv.text =
            holder.context.getString(R.string.chance_probability_template, item.chanceOfSnow)
    }
}

private class MainHourAdapterDiffCallback : DiffUtil.ItemCallback<LocalHour>() {
    override fun areItemsTheSame(oldItem: LocalHour, newItem: LocalHour): Boolean =
        oldItem.timeDate == newItem.timeDate

    override fun areContentsTheSame(
        oldItem: LocalHour,
        newItem: LocalHour
    ): Boolean = oldItem.numberHour == newItem.numberDay && oldItem.numberDay == newItem.numberDay

}