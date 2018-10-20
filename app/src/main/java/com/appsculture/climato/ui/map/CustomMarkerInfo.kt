package com.appsculture.climato.ui.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast

class CustomMarkerInfo(context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    val mContext = context

    override fun getInfoContents(p0: Marker?): View {
        val view = (mContext as Activity).layoutInflater.inflate(R.layout.marker_info_layout, null)
        val cityNameTextView = view.findViewById(R.id.cityNameTextView) as TextView
        val cityDetailsTextView = view.findViewById(R.id.cityDetailsTextView) as TextView
        val minTempTextView = view.findViewById(R.id.minTempTextView) as TextView
        val maxTempTextView = view.findViewById(R.id.maxTempTextView) as TextView
        val pressureTextView = view.findViewById(R.id.pressureTextView) as TextView

        cityNameTextView.text = (p0 as Marker).title
        cityDetailsTextView.text = (p0 as Marker).snippet

        val forecast = (p0 as Marker).tag as Forecast
        minTempTextView.text = forecast.temperature.min.toString()
        maxTempTextView.text = forecast.temperature.max.toString()
        pressureTextView.text = forecast.temperature.pressure.toString()

        return view
    }

}