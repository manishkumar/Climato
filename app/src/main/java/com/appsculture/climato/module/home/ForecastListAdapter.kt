package com.appsculture.climato.module.home

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appsculture.climato.R
import com.appsculture.climato.app.Constants
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.utils.WeatherDataFormatter
import java.util.*

class ForecastListAdapter(
    private val forecasts: ArrayList<Forecast>,
    private val formatter: WeatherDataFormatter,
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
        var name = itemView.findViewById<TextView>(R.id.tvName)
        var description = itemView.findViewById<TextView>(R.id.tvDescription)
        var pressure = itemView.findViewById<TextView>(R.id.tvPressure)
        var humidity = itemView.findViewById<TextView>(R.id.tvHumidity)
        var temperature = itemView.findViewById<TextView>(R.id.tvTemperature)
        var icon = itemView.findViewById<TextView>(R.id.ivIcon)
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
        holder.description.text = forecast.weather?.main
        holder.pressure.text =
                String.format(
                    holder.view.context.getString(R.string.pressure_value),
                    forecast.main?.pressure
                )
        holder.humidity.text =
                String.format(
                    holder.view.context.getString(R.string.humidity_value),
                    forecast.main?.humidity
                )
        holder.temperature.text =
                formatter.prettyKelvinToCelsius(forecast.main?.temperature)
        holder.view.setOnClickListener {
            listener.clicked(forecast)
        }

        val id = forecast.weather?.id.let { it!! }
        val date = forecast.date.let { it!! }
        val weatherFont =
            Typeface.createFromAsset(holder.view.context.getAssets(), Constants.fontPath)
        holder.icon.setTypeface(weatherFont)
        holder.icon.setText(formatter.weatherIcon(id, date))

    }

    fun addForecastToList(forecast: List<Forecast>) {
        forecasts.clear()
        forecasts.addAll(forecast)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun clicked(item: Forecast)
    }

}
