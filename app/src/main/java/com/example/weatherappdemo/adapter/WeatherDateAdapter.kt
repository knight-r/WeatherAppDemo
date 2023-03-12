package com.example.weatherappdemo.adapter

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.weatherappdemo.R
import com.example.weatherappdemo.databinding.ActivityShowWeatherPageBinding
import com.example.weatherappdemo.databinding.RowDateItemBinding
import com.example.weatherappdemo.model.Forecastday
import com.example.weatherappdemo.model.Hour

class WeatherDateAdapter(private val binding: ActivityShowWeatherPageBinding, private val forecastDayList: List<Forecastday>) : RecyclerView.Adapter<WeatherDateAdapter.ViewHolder>() {
    private var _context: Context? = null
    private lateinit var _binding: RowDateItemBinding
    private var selectedPosition:Int =0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        _context = parent.context
        _binding = RowDateItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_date_item, parent, false)
        return ViewHolder(view)
    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
//        holder.apply {
//            _binding.tvDate.text = forecastDayList?.get(position)?.date
//            _binding.tvDay.text = forecastDayList?.get(position)?.day.toString()
//        }

        holder.textViewDate.text = forecastDayList?.get(position)?.date
        holder.textViewAvgTemp.text =  forecastDayList?.get(position)?.day?.avgtemp_c.toString() + " °C"
        holder.textViewAvgWS.text =  forecastDayList?.get(position)?.day?.avgvis_km.toString() + " km/h"

        holder.imageViewIcon.load( "https://" + forecastDayList.get(position).hour.get(0).condition.icon) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
        holder.itemView.setOnClickListener{
            selectedPosition = position
            val hourList: List<Hour> = forecastDayList[position].hour.reversed()
            binding?.rvHourInfo?.apply {

                layoutManager = LinearLayoutManager(_context)
                adapter = WeatherHourAdapter(hourList)
            }
            notifyDataSetChanged()

        }

        if(selectedPosition == position) {
            holder.cardViewDate.setBackgroundResource(R.drawable.round_edge_item)
        }else {
            holder.cardViewDate.setBackgroundResource(R.drawable.item_change_color)


        }
    }
    override fun getItemCount(): Int {
        return forecastDayList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.tv_date)
        val textViewAvgTemp: TextView = itemView.findViewById(R.id.tv_avg_temp)
        val textViewAvgWS: TextView = itemView.findViewById(R.id.tv_avg_wind_speed)
        val imageViewIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val cardViewDate: CardView = itemView.findViewById(R.id.cv_date)
    }
}