package com.appsculture.climato.module.map

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.appsculture.climato.data.ForecastRepository
import com.appsculture.climato.model.Forecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MapViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {

    companion object {
        const val timeout: Long = 400
    }

    var allForecastsResult: MutableLiveData<List<Forecast>> = MutableLiveData()
    var allForecastsError: MutableLiveData<String> = MutableLiveData()

    private lateinit var disposableObserver: DisposableObserver<List<Forecast>>


    fun loadSavedForecasts() {
        disposableObserver = object : DisposableObserver<List<Forecast>>() {
            override fun onComplete() {
            }

            override fun onNext(forecasts: List<Forecast>) {
                allForecastsResult.postValue(forecasts)
            }

            override fun onError(e: Throwable) {
                allForecastsError.postValue(e.message)
            }
        }

        forecastRepository.getSavedForecasts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(timeout, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}