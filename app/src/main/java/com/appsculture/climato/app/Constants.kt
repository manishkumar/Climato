package com.appsculture.climato.app

object APIConstants {
    const val baseUrl = "http://api.openweathermap.org/data/2.5/"
    const val apiKey = "f3a52babb832d59696c3804837596ced"
}

object DBConstants {
    const val name = "forecast_db"
}

object Constants {
    const val fontPath = "fonts/weather.ttf"
    internal const val WORKER_TAG = "sync_worker"
    const val refreshIntervalKey = "refresh_interval"
    const val defaultInterval = "15"
}