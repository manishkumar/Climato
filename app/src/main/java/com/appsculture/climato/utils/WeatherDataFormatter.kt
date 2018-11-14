package com.appsculture.climato.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.format.DateFormat
import com.appsculture.climato.R
import java.util.*
import javax.inject.Inject


class WeatherDataFormatter {

    private val context: Context
    private val sharedPreferences: SharedPreferences

    @Inject
    constructor(context: Context) {
        this.context = context
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun convertTemperature(temperature: Double): String {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val unit = context.getString(R.string.unit_key)
        val metric = context.getString(R.string.unit_metric_value)
        val imperial = context.getString(R.string.unit_imperial_value)

        if (sharedPreferences.getString(unit, metric) == metric) {
            return prettyKelvinToCelsius(temperature)
        } else if (sharedPreferences.getString(unit, metric) == imperial) {
            return prettyKelvinToFahrenheit(temperature)
        } else {
            return temperature.toString()
        }
    }

    fun kelvinToCelsius(temperature: Double): Double {
        return (Math.round(temperature - 273.15F)).toDouble()
    }

    fun prettyKelvinToCelsius(temperature: Double): String {
        val converted = kelvinToCelsius(temperature)
        return "$converted ${context.getString(R.string.celsius)}"
    }

    fun kelvinToFahrenheit(temperature: Double): Double {
        return (Math.round((kelvinToCelsius(temperature) * 9.0 / 5.0) + 32.0)).toDouble()
    }

    fun prettyKelvinToFahrenheit(temperature: Double): String {
        val converted = kelvinToFahrenheit(temperature)
        return "$converted ${context.getString(R.string.fahrenheit)}"
    }

    fun formatTimestamp(timeStamp: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timeStamp * 1000L
        return DateFormat.format("hh:mm a", cal).toString()
    }

    fun prettyPressure(pressure: Double?): String {
        return String.format(context.getString(R.string.pressure), pressure)
    }

    fun prettyHumidity(humidity: Int?): String {
        return String.format(context.getString(R.string.humidity), humidity)
    }

    fun weatherIcon(actualId: Int, date: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = date * 1000
        var hourOfDay = cal.get(Calendar.HOUR_OF_DAY)

        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
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