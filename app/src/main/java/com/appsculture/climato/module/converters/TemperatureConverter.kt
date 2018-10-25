package com.appsculture.climato.module.converters

import android.content.Context
import com.appsculture.climato.R
import javax.inject.Inject

class TemperatureConverter @Inject constructor(private val context: Context) {

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

}