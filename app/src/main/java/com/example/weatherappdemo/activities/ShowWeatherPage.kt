package com.example.weatherappdemo.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappdemo.adapter.WeatherDateAdapter
import com.example.weatherappdemo.adapter.WeatherHourAdapter
import com.example.weatherappdemo.databinding.ActivityShowWeatherPageBinding
import com.example.weatherappdemo.model.Forecastday
import com.example.weatherappdemo.model.Hour
import com.example.weatherappdemo.model.WeatherData
import com.example.weatherappdemo.services.GetDataService
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class ShowWeatherPage : AppCompatActivity() {
    private lateinit var _binding: ActivityShowWeatherPageBinding
    private var progressDialog: ProgressDialog? = null
   private lateinit var forecastHourList :List<Hour>

   @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShowWeatherPageBinding.inflate(layoutInflater)
        setContentView(_binding.root)


        val bundle :Bundle ?=intent.extras
        val cityName = bundle!!.getString("city_name")
        _binding.tvCityName.text = cityName
        progressDialog = ProgressDialog(this@ShowWeatherPage)
        progressDialog!!.setMessage("Loading....")
        forecastHourList = emptyList()
        if (cityName != null) {
            progressDialog!!.show()
            fetchInfo(cityName)

        }


    }
    private fun fetchInfo(cityName:String){

        val retrofitService = getRetrofitClient().getInfo("68ea7c5dd8244ec5a88111310210311",cityName,10,"no","no")
        // http://api.weatherapi.com/v1/forecast.json?key=68ea7c5dd8244ec5a88111310210311&q=London&days=10&aqi=no&alerts=no


        retrofitService.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: retrofit2.Call<WeatherData?>, response: Response<WeatherData?>){
                val myDataList = response.body()!!
                generateDataList(myDataList)

                _binding?.rvHourInfo?.apply {
                    layoutManager = LinearLayoutManager(this@ShowWeatherPage)
                    adapter = WeatherHourAdapter(forecastHourList)
                }
//                _binding?.rvHourInfo?.layoutManager = LinearLayoutManager(
//                    this@ShowWeatherPage,
//                    LinearLayoutManager.HORIZONTAL,
//                    false
//                )
                progressDialog!!.cancel()
            }

            override fun onFailure(call: retrofit2.Call<WeatherData?>, t: Throwable) {
                progressDialog?.cancel()
                Toast.makeText(this@ShowWeatherPage,"Error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getRetrofitClient(): GetDataService {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GetDataService::class.java)
    }
    fun generateDataList(weatherData: WeatherData){
        _binding?.rvWeatherInfo?.apply {
            layoutManager = LinearLayoutManager(this@ShowWeatherPage)
            val forecastDayList = weatherData.forecast.forecastday
            if(forecastDayList.isNotEmpty()){
                val len:Int = forecastDayList.size
                forecastHourList = forecastDayList[len-1].hour
            }
            adapter = WeatherDateAdapter(_binding,forecastDayList)
        }
        _binding.rvWeatherInfo.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

    }
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/"
    }

}