package com.appsculture.climato.module.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.utils.WeatherDataFormatter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapMarkerInfo(private val context: Context, private val formatter: WeatherDataFormatter) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }

    override fun getInfoContents(marker: Marker?): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.marker_info_layout, null)

        val name = view.findViewById(R.id.tvName) as TextView
        val description = view.findViewById(R.id.tvDescription) as TextView
        val temperature = view.findViewById(R.id.tvTemperature) as TextView

        val forecast = marker?.tag as Forecast
        name.text = forecast.name
        description.text = forecast.weather?.main
        temperature.text = formatter.prettyKelvinToCelsius(forecast.main?.temperature)
        //temperature.text = forecast
        return view
    }

}