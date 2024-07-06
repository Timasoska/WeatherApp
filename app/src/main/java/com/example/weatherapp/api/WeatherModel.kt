package com.example.weatherapp.api


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)