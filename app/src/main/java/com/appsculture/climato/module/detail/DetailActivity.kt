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
        tvCity.text = forecast.name
        tvCountry.text = forecast.sys?.country
        tvCondition.text = forecast.weather?.main
        tvTempMax.text = formatter.convertTemperature(forecast.main?.tempMax)
        tvTempMin.text = formatter.convertTemperature(forecast.main?.tempMin)
        tvHumidity.text = formatter.prettyHumidity(forecast.main?.humidity)
        tvPressure.text = formatter.prettyPressure(forecast.main?.pressure)
        tvTemperature.text = formatter.convertTemperature(forecast.main?.temperature)

        val id = forecast.weather?.id.let { it!! }
        val date = forecast.date.let { it!! }
        val weatherFont = Typeface.createFromAsset(getAssets(), Constants.fontPath)
        ivIcon.setTypeface(weatherFont)
        ivIcon.setText(formatter.weatherIcon(id, date))
    }
}
