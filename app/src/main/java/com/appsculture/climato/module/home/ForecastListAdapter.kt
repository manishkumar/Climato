package com.appsculture.climato.module.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.converters.TemperatureConverter
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*
import javax.inject.Inject

class ForecastListAdapter(private val forecasts: ArrayList<Forecast>, private val temperatureConverter: TemperatureConverter) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.tvName)
        var description = itemView.findViewById<TextView>(R.id.tvDescription)
        var pressure = itemView.findViewById<TextView>(R.id.tvPressure)
        var humidity = itemView.findViewById<TextView>(R.id.tvHumidity)
        var temperature = itemView.findViewById<TextView>(R.id.tvTemperature)
        var icon = itemView.findViewById<SimpleDraweeView>(R.id.ivIcon)
        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.place_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecasts[position]
        holder.name.text = forecast.name
        holder.description.text = forecast.weather?.description
        holder.pressure.text = String.format(holder.context.getString(R.string.pressure), forecast.main?.pressure)
        holder.humidity.text = String.format(holder.context.getString(R.string.humidity), forecast.main?.humidity)
        holder.temperature.text = temperatureConverter.prettyKelvinToCelsius(forecast.main?.temperature)
    }

    fun addForecastToList(forecast: List<Forecast>) {
        val initPosition = forecasts.size
        forecasts.addAll(forecast)
        notifyItemRangeInserted(initPosition, forecasts.size)
    }

}
