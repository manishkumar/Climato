package com.appsculture.climato.network

import com.appsculture.climato.model.WeatherData

interface ResponseCallback {
    fun onSuccess(response: WeatherData)
    fun onFailure(error: String)
}
