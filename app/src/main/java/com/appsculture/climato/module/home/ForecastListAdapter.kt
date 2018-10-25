package com.appsculture.climato.module.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

class ForecastListAdapter(private val forecasts: ArrayList<Forecast>) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.tvName)
        var forecast = itemView.findViewById<TextView>(R.id.tvForecast)
        var icon = itemView.findViewById<SimpleDraweeView>(R.id.ivIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecasts[position]
        holder.name.text = forecast.name
        holder.forecast.text = forecast.weather?.main
    }

    fun addForecastToList(forecast: List<Forecast>) {
        val initPosition = forecasts.size
        forecasts.addAll(forecast)
        notifyItemRangeInserted(initPosition, forecasts.size)
    }

}
