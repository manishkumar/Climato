package com.appsculture.climato.utils

import android.content.Context
import android.net.Uri
import com.appsculture.climato.R
import com.appsculture.climato.app.APIConstants
import java.util.*
import javax.inject.Inject

class WeatherDataFormatter @Inject constructor(private val context: Context) {

    fun kelvinToCelsius(temperature: Double?): Double {
        val temperature = temperature.let { it!! }
        return (Math.round(temperature - 273.15F)).toDouble()
    }

    fun prettyKelvinToCelsius(temperature: Double?): String {
        val temperature = temperature.let { it!! }
        val converted = kelvinToCelsius(temperature)
        return "$converted ${context.getString(R.string.celsius)}"
    }


    fun kelvinToFahrenheit(temperature: Double?): Double {
        return (Math.round((kelvinToCelsius(temperature) * 9.0 / 5.0) + 32.0)).toDouble()
    }

    fun prettyKelvinToFahrenheit(temperature: Double?): String {
        val temperature = temperature.let { it!! }
        val converted = kelvinToFahrenheit(temperature)
        return "$converted ${context.getString(R.string.fahrenheit)}"
    }

    fun prettyPressure(pressure: Double?): String {
        return String.format(context.getString(R.string.pressure), pressure)
    }

    fun prettyHumidity(humidity: Int?): String {
        return String.format(context.getString(R.string.humidity), humidity)
    }

    fun iconUrl(icon: String?): Uri {
        val url = APIConstants.iconUrl + icon + APIConstants.iconExtension
        return Uri.parse(url)
    }

    fun weatherIcon(actualId: Int, date: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = date * 1000
        val hourOfDay = cal.get(Calendar.HOUR_OF_DAY)

        val id = actualId / 100
        var icon = ""
        if (id == 800) {
            if (hourOfDay >= 7 && hourOfDay < 20) {
                icon = context.getString(R.string.weather_sunny)
            } else {
                icon = context.getString(R.string.weather_clear_night)
            }
        } else {
            when (id) {
                2 -> icon = context.getString(R.string.weather_thunder)
                3 -> icon = context.getString(R.string.weather_drizzle)
                7 -> icon = context.getString(R.string.weather_foggy)
                8 -> icon = context.getString(R.string.weather_cloudy)
                6 -> icon = context.getString(R.string.weather_snowy)
                5 -> icon = context.getString(R.string.weather_rainy)
            }
        }
        return icon
    }

}