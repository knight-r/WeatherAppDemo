package com.example.weatherappdemo.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappdemo.adapter.WeatherDateAdapter
import com.example.weatherappdemo.adapter.WeatherHourAdapter
import com.example.weatherappdemo.databinding.ActivityShowWeatherPageBinding
import com.example.weatherappdemo.model.*
import com.example.weatherappdemo.services.GetDataService
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ShowWeatherPage : AppCompatActivity() {
    private lateinit var _binding: ActivityShowWeatherPageBinding
//    private var progressDialog: ProgressDialog? = ProgressDialog(applicationContext)
    private lateinit var forecastHourList :List<Hour>
    private val CITY_NAME: String = "1"
    private val PINCODE: String = "2"
    private var retrofitAPI1: GetDataService? = null
    private var retrofitAPI2: GetDataService? = null
   @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShowWeatherPageBinding.inflate(layoutInflater)
        setContentView(_binding.root)
       val bundle :Bundle ?=intent.extras
       _binding.tvCityName.text = "No Data Found"

       bundle?.let {

           var inputType = it.getInt("input_type", 1)
           var inputData = it.getString("input_data")
           Log.e("Ayush#1", inputType.toString())
           if (inputType.toString() == CITY_NAME)  {
               _binding.tvCityName.text = inputData
               inputData?.let {
                       input ->
                   fetchInfoWithCityName(input)
               }
           } else if (inputType.toString() == PINCODE) {
               Log.e("Ayush#2", inputType.toString())
               inputData?.let {
                       input ->
                   getCityNameFromPinCode(input)
               }
           } else {
               finish()
           }
       }
    }

    private fun getCityNameFromPinCode(pinCode: String) {

        val retrofitService = getRetrofitInstance(BASE_URL_PINCODE)?.getCityByPinCode(pinCode)
        retrofitService?.enqueue( object : Callback<Pincode?> {
            override fun onResponse(call: retrofit2.Call<Pincode?>, response: Response<Pincode?>){
                val responseData: ArrayList<PincodeItem>? = response.body()
                val dataList:ArrayList<PostOffice> = responseData?.get(0)?.PostOffice as ArrayList<PostOffice>
               val cityName:String = dataList[0].District
                _binding.tvCityName.text = cityName
                fetchInfoWithCityName(cityName)
            }
            override fun onFailure(call: retrofit2.Call<Pincode?>, t: Throwable) {
                _binding.tvCityName.text = "Data not found "
                Toast.makeText(this@ShowWeatherPage,"Error occurred!",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchInfoWithCityName(cityName:String){

        val retrofitService = getRetrofitInstance(BASE_URL_WEATHER)?.getInfo(API_KEY,cityName,10,"no","no")
        retrofitService?.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: retrofit2.Call<WeatherData?>, response: Response<WeatherData?>){
                val myDataList = response.body()!!
                generateDataList(myDataList)

                _binding?.rvHourInfo?.apply {
                    layoutManager = LinearLayoutManager(this@ShowWeatherPage)
                    adapter = WeatherHourAdapter(forecastHourList)
                }

            }

            override fun onFailure(call: retrofit2.Call<WeatherData?>, t: Throwable) {
                Toast.makeText(this@ShowWeatherPage,"Error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getRetrofitInstance(baseUrlValue: String): GetDataService? {
        var retrofit: GetDataService? = null
        if (BASE_URL_WEATHER == baseUrlValue) {
            if (retrofitAPI1 == null) {
                retrofitAPI1 = Retrofit.Builder().addConverterFactory(
                    GsonConverterFactory.create()
                )
                    .baseUrl(BASE_URL_WEATHER)
                    .build()
                    .create(GetDataService::class.java)
            }
            retrofit = retrofitAPI1
        } else if(BASE_URL_PINCODE == baseUrlValue) {
            if (retrofitAPI2 == null ) {
                retrofitAPI2 = Retrofit.Builder().addConverterFactory(
                    GsonConverterFactory.create()
                )
                    .baseUrl(BASE_URL_PINCODE)
                    .build()
                    .create(GetDataService::class.java)
            }
            retrofit = retrofitAPI2
        }
        return retrofit
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
        const val BASE_URL_WEATHER = "https://api.weatherapi.com/"
        const val BASE_URL_PINCODE = "https://api.postalpincode.in/"
        const val API_KEY = "68ea7c5dd8244ec5a88111310210311"
    }

}