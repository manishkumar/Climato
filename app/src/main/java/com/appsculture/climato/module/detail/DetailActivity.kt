package com.appsculture.climato.module.detail

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appsculture.climato.R
import com.appsculture.climato.app.Constants
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.utils.WeatherDataFormatter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {
    companion object {
        const val FORECAST = "forecast"
    }

    @Inject
    lateinit var formatter: WeatherDataFormatter
    private lateinit var forecast: Forecast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        AndroidInjection.inject(this)

        forecast = intent.getParcelableExtra(FORECAST)
        initViews()
    }

    private fun initViews() {
        tvCity.text = forecast.city?.name
        tvCountry.text = forecast.city?.country
        tvCondition.text = forecast.weather?.main
        tvTempMax.text = formatter.prettyKelvinToCelsius(forecast.main?.tempMax)
        tvTempMin.text = formatter.prettyKelvinToCelsius(forecast.main?.tempMin)
        tvHumidity.text = formatter.prettyHumidity(forecast.main?.humidity)
        tvPressure.text = formatter.prettyPressure(forecast.main?.pressure)
        tvTemperature.text = formatter.prettyKelvinToCelsius(forecast.main?.temperature)

        val weatherFont = Typeface.createFromAsset(getAssets(), Constants.fontPath)
        ivIcon.setTypeface(weatherFont)
        forecast.weather?.id.let {
            ivIcon.setText(formatter.weatherIcon(it!!, Calendar.HOUR_OF_DAY))
        }
    }
}
