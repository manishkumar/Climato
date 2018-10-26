package com.appsculture.climato.data

import com.appsculture.climato.data.local.ForecastDao
import com.appsculture.climato.data.remote.APIInterface
import com.appsculture.climato.model.Forecast
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ForecastRepository @Inject constructor(
    val apiInterface: APIInterface,
    val forecastDao: ForecastDao
) {
    companion object {
        const val CITY_NOT_FOUND = "404"
    }

    fun getForecastFromApi(searchTerm: String): Single<Forecast> {
        return Single.create {
            val emitter = it
            apiInterface.getWeatherData(searchTerm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.cod == CITY_NOT_FOUND) {
                        emitter.onError(Throwable(it.message))
                    } else {
                        val forecast = it.list.first()
                        forecast.id = it.city.id
                        forecast.city = it.city
                        forecast.weather = forecast.weathers?.first()
                        saveForecast(forecast)
                        emitter.onSuccess(forecast)
                    }
                }, {
                    emitter.onError(it)
                })
        }
    }

    fun getSavedForecasts(): Observable<List<Forecast>> {
        return forecastDao.forecasts()
            .toObservable()
    }

    fun getSavedForecast(id: Int): Single<Forecast> {
        return forecastDao.forecast(id)
    }

    private fun saveForecast(forecast: Forecast) {
        Single.create<Unit> { forecastDao.insertForecast(forecast) }
            .subscribeOn(Schedulers.io())
            .subscribe()

    }

}