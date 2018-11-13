package com.appsculture.climato.module.detail

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
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
        configureToolbar()
        tvCity.text = forecast.name
        tvCountry.text = forecast.sys?.country
        tvCondition.text = forecast.weather?.main
        tvHumidity.text = formatter.prettyHumidity(forecast.main?.humidity)
        tvPressure.text = formatter.prettyPressure(forecast.main?.pressure)

        forecast.main?.tempMax?.let {
            tvTempMax.text = formatter.convertTemperature(it)
        }

        forecast.main?.tempMin?.let {
            tvTempMin.text = formatter.convertTemperature(it)
        }


        forecast.main?.temperature?.let {
            tvTemperature.text = formatter.convertTemperature(it)
        }

        forecast.date?.let {
            tvUpdatedAt.text = formatter.formatTimestamp(it)
        }

        val id = forecast.weather?.id.let { it!! }
        val date = forecast.date.let { it!! }
        val weatherFont = Typeface.createFromAsset(getAssets(), Constants.fontPath)
        ivIcon.setTypeface(weatherFont)
        ivIcon.setText(formatter.weatherIcon(id, date))
    }

    private fun configureToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
