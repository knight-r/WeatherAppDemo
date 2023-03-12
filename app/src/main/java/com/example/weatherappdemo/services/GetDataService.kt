package com.example.weatherappdemo.services

import com.example.weatherappdemo.model.Pincode
import com.example.weatherappdemo.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService{
    @GET("v1/forecast.json?")
    fun getInfo(@Query("key" ) apiKey:String,
                @Query("q" ) cityName:String,
                @Query("days" ) days:Int,
                @Query("alerts" ) alerts:String,
                @Query("aqi" ) aqi:String):
            Call<WeatherData>

    // http://api.weatherapi.com/v1/forecast.json?key=68ea7c5dd8244ec5a88111310210311&q=London&days=10&aqi=no&alerts=no


    @GET("pincode/{pin_code}")
    fun getCityByPinCode(@Path("pin_code") pinCode : String): Call<Pincode>

}