package com.appsculture.climato.module.home

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

class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {

    companion object {
        const val timeout: Long = 400
    }

    var allForecastsResult: MutableLiveData<List<Forecast>> = MutableLiveData()
    var allForecastsError: MutableLiveData<String> = MutableLiveData()

    var forecastSearchResult: MutableLiveData<Forecast> = MutableLiveData()
    var forecastSearchError: MutableLiveData<String> = MutableLiveData()

    private lateinit var disposableObserver: DisposableObserver<List<Forecast>>
    private var searchSubscription: Disposable? = null


    fun searchForecast(searchTerm: String) {
        searchSubscription = forecastRepository.getForecastFromApi(searchTerm)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                forecastSearchResult.postValue(it)
            }, {
                forecastSearchError.postValue(it.message)
            })
    }

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
        searchSubscription?.dispose()
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}