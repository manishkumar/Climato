package com.appsculture.climato.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    val city: City,
    @SerializedName("cod")
    val messageCode: Int,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("list")
    val forecasts: List<Forecast>
)