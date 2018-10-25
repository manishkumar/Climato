package com.appsculture.climato.data

import android.util.Log
import com.appsculture.climato.data.local.ForecastDao
import com.appsculture.climato.data.remote.APIInterface
import com.appsculture.climato.model.Forecast
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ForecastRepository @Inject constructor(val apiInterface: APIInterface, val forecastDao: ForecastDao) {

    fun getForecastFromApi(searchTerm: String): Single<Forecast> {
        return Single.create {
            val emitter = it
            apiInterface.getWeatherData(searchTerm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                    val forecast = it.list.first()
                    forecast.id = it.city.id
                    forecast.name = it.city.name
                    forecast.weather = forecast.weathers?.first()
                    saveForecast(forecast)
                    emitter.onSuccess(forecast)
                }, {
                    val message = it.message
                    print(message)
                })
        }
    }

    fun getSavedForecasts(): Observable<List<Forecast>> {
        return forecastDao.forecasts()
            .toObservable()
            .doOnNext {
                print(it.size)
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
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