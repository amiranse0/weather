package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.data.model.local.LocalForecast
import com.example.weather.databinding.DayCardViewBinding
import com.example.weather.util.urlToBitmap

class MainDaysAdapter : ListAdapter<LocalForecast, MainDaysAdapter.DayViewHolder>(
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

        holder.binding.conditionIconTv.load(item.conditionIcon){
            error(R.drawable.ic_baseline_broken_image_24)
        }

    }
}

class MainDayDiffCallback : DiffUtil.ItemCallback<LocalForecast>() {
    override fun areItemsTheSame(oldItem: LocalForecast, newItem: LocalForecast): Boolean =
        oldItem.numberDay == newItem.numberDay

    override fun areContentsTheSame(oldItem: LocalForecast, newItem: LocalForecast): Boolean {
        return oldItem.numberDay == newItem.numberDay
    }
}