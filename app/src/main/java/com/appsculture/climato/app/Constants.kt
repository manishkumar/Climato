package com.appsculture.climato.app

import com.appsculture.climato.BuildConfig

object APIConstants {
    const val baseUrl = "http://api.openweathermap.org/data/2.5/"
    const val apiKey = BuildConfig.apiKey
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