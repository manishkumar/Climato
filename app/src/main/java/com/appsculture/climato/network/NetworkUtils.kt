package com.appsculture.climato.network

import com.appsculture.climato.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.net.HttpURLConnection
import java.util.*


interface OpenWeatherMapService {
    @GET("forecast")
    fun getForecast(@QueryMap options: Map<String, String>): Call<WeatherData>
}

object NetworkUtils {

    private val OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/"
    private val FORECAST_BASE_URL = OPEN_WEATHER_MAP_URL

    private val apiKey = "f3a52babb832d59696c3804837596ced"
    private val format = "json"
    private val units = "metric"
    private val numDays = 14

    /* API key parameter */
    private val APP_ID = "APPID"
    private val QUERY_PARAM = "q"
    private val LAT_PARAM = "lat"
    private val LON_PARAM = "lon"

    private val FORMAT_PARAM = "mode"
    private val UNITS_PARAM = "units"
    private val DAYS_PARAM = "cnt"


    private var retrofit: Retrofit? = null


    private fun getDefaultQueryMap(): HashMap<String, String> {
        return hashMapOf(
            APP_ID to apiKey,
            FORMAT_PARAM to format,
            UNITS_PARAM to units,
            DAYS_PARAM to Integer.toString(numDays)
        )
    }

    fun getResponseFromRetrofit(queryMap: Map<String, String>, callback: ResponseCallback) {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(FORECAST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        retrofit?.let {
            it.create(OpenWeatherMapService::class.java).getForecast(queryMap).enqueue(object :
                Callback<WeatherData> {
                override fun onResponse(
                    call: Call<WeatherData>,
                    response: Response<WeatherData>
                ) {
                    if (response.isSuccessful && response.body()?.messageCode == HttpURLConnection.HTTP_OK) {
                        val openWeatherMapResponse = response.body() as WeatherData
                        callback.onSuccess(openWeatherMapResponse)
                    } else {
                        callback.onFailure("Failed to get")
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    t.printStackTrace()
                    callback.onFailure("Failed to get")
                }
            })
        }
    }
}