package com.appsculture.climato.module.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.appsculture.climato.data.ForecastRepository
import com.appsculture.climato.model.Forecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) : ViewModel() {

    companion object {
        const val timeout: Long = 400
    }

    private var allForecastsResult: MutableLiveData<List<Forecast>> = MutableLiveData()
    private var allForecastsError: MutableLiveData<String> = MutableLiveData()
    private var allForecastsLoader: MutableLiveData<Boolean> = MutableLiveData()

    private var forecastSearchResult: MutableLiveData<Forecast> = MutableLiveData()
    private var forecastSearchError: MutableLiveData<String> = MutableLiveData()
    private var forecastSearchLoader: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var disposableObserver: DisposableObserver<List<Forecast>>
    private var searchSubscription: Disposable? = null

    fun forecastsResult(): LiveData<List<Forecast>> {
        return allForecastsResult
    }

    fun forecastsError(): LiveData<String> {
        return allForecastsError
    }

    fun forecastsLoader(): LiveData<Boolean> {
        return allForecastsLoader
    }

    fun searchForecast(searchTerm: String) {
        searchSubscription = forecastRepository.getForecastFromApi(searchTerm)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                forecastSearchResult.postValue(it)
                forecastSearchLoader.postValue(false)
            }, {
                forecastSearchError.postValue(it.message)
                forecastSearchLoader.postValue(false)
            })
    }

    fun loadSavedForecasts() {
        disposableObserver = object : DisposableObserver<List<Forecast>>() {
            override fun onComplete() {
            }

            override fun onNext(forecasts: List<Forecast>) {
                allForecastsResult.postValue(forecasts)
                allForecastsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                allForecastsError.postValue(e.message)
                allForecastsLoader.postValue(false)
            }
        }

        forecastRepository.getSavedForecasts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(timeout, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        searchSubscription?.dispose()
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}