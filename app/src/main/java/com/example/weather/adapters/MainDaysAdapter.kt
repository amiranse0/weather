package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.model.local.Forecast
import com.example.weather.databinding.DayCardViewBinding

class MainDaysAdapter : ListAdapter<Forecast, MainDaysAdapter.DayViewHolder>(
    MainDayDiffCallback()
) {

    class DayViewHolder(
        val binding: DayCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DayCardViewBinding.inflate(inflater)

        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.dateDayTv.text = item.date
        holder.binding.maxTemp.text =
            holder.context.getString(R.string.temperature_template, item.maximumTemperature)
        holder.binding.minTemp.text =
            holder.context.getString(R.string.temperature_template, item.minimumTemperature)
        holder.binding.rainProbabilityTv.text =
            holder.context.getString(R.string.chance_probability_template, item.dailyChanceOfRain)
        holder.binding.snowProbabilityTv.text =
            holder.context.getString(R.string.chance_probability_template, item.dailyChanceOfSnow)
    }
}

class MainDayDiffCallback : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
        oldItem.forecast == newItem.forecast

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem.numberDay == newItem.numberDay
    }
}