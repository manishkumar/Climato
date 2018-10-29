package com.appsculture.climato.module.map

import android.app.Activity
import android.content.Context
import android.view.View
import com.appsculture.climato.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapMarkerInfo(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    override fun getInfoContents(p0: Marker?): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.marker_info_layout, null)

        return view
    }

}