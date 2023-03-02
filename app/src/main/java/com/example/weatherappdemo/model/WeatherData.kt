package com.example.weatherappdemo.model

data class WeatherData(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)