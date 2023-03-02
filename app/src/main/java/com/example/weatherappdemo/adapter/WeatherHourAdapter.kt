package com.example.weatherappdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.weatherappdemo.R
import com.example.weatherappdemo.model.Hour

class WeatherHourAdapter(private val forecastHourList: List<Hour>) : RecyclerView.Adapter<WeatherHourAdapter.ViewHolder>() {
    private var _context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_info_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewTime.text = forecastHourList?.get(position)?.time.toString()
        holder.textViewTemp.text = "Temperature: " + forecastHourList?.get(position)?.temp_c.toString() + " C"
        holder.textViewFL.text = "Temperature Feels Like: " + forecastHourList?.get(position)?.feelslike_c.toString()+ " C"
        holder.textViewWS.text = "WindSpeed: " + forecastHourList?.get(position)?.wind_kph.toString() + " km/h"
        holder.textViewWD.text = "WindDirection: " + forecastHourList?.get(position)?.wind_dir.toString()
        holder.textViewPressure.text = "Air Pressure: " + forecastHourList?.get(position)?.pressure_in.toString()
        holder.imageIcon.load( "https://" +forecastHourList[position].condition.icon) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }

    }

    override fun getItemCount(): Int {
        return forecastHourList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTime: TextView = itemView.findViewById(R.id.tv_time)
        val textViewTemp: TextView = itemView.findViewById(R.id.tv_info_temp)
        val textViewFL: TextView = itemView.findViewById(R.id.tv_feels_like)
        val imageIcon: ImageView = itemView.findViewById(R.id.iv_info_icon)
        val textViewWS: TextView = itemView.findViewById(R.id.tv_info_wind_speed)
        val textViewWD: TextView = itemView.findViewById(R.id.tv_wind_dir)
        val textViewPressure: TextView = itemView.findViewById(R.id.tv_pressure)



    }
}