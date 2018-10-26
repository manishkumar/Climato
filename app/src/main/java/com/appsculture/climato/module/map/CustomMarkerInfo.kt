package com.appsculture.climato.module.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.appsculture.climato.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomMarkerInfo(context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    val mContext = context

    override fun getInfoContents(p0: Marker?): View {
        val view = (mContext as Activity).layoutInflater.inflate(R.layout.marker_info_layout, null)
        val cityNameTextView = view.findViewById(R.id.cityNameTextView) as TextView
        val cityDetailsTextView = view.findViewById(R.id.cityDetailsTextView) as TextView
        cityNameTextView.text = p0?.title
        cityDetailsTextView.text = p0?.snippet
        return view
    }

}