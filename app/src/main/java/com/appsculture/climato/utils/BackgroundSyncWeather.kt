package com.appsculture.climato.utils

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import androidx.work.Worker
import com.appsculture.climato.data.ForecastRepository
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.home.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BackgroundSyncWeather : Worker() {

    private lateinit var forecastRepository: ForecastRepository
    private lateinit var disposableObserver: DisposableObserver<List<Forecast>>
    var allForecastsResult: MutableLiveData<List<Forecast>> = MutableLiveData()
    var allForecastsError: MutableLiveData<String> = MutableLiveData()

    override fun doWork(): WorkerResult {
        Log.d("Jeetu", "in side doWork()")
        val resolver = applicationContext.contentResolver
        try {
            getForeCastFromRepository()
            return WorkerResult.SUCCESS
        } catch (t: Throwable) {
            return WorkerResult.FAILURE
        }
    }

    private fun getForeCastFromRepository() {
        disposableObserver = object : DisposableObserver<List<Forecast>>() {
            override fun onComplete() {
            }

            override fun onNext(forecasts: List<Forecast>) {
                allForecastsResult.postValue(forecasts)
                Log.d("Jeetu", "work is done ${allForecastsResult.value}")
            }

            override fun onError(e: Throwable) {
                allForecastsError.postValue(e.message)
            }
        }

        forecastRepository.getSavedForecasts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(HomeViewModel.timeout, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

}