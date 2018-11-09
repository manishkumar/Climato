package com.appsculture.climato.worker

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.appsculture.climato.app.APIConstants
import com.appsculture.climato.app.ClimatoApplication
import com.appsculture.climato.data.ForecastRepository
import com.appsculture.climato.di.component.DaggerAppComponent
import com.appsculture.climato.di.modules.APIModule
import com.appsculture.climato.di.modules.AppModule
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.home.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherSyncWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    @Inject
    lateinit var forecastRepository: ForecastRepository

    private lateinit var disposableObserver: DisposableObserver<List<Forecast>>

    override fun doWork(): Result {
        if(applicationContext is ClimatoApplication){
            DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext as ClimatoApplication))
                .aPIModule(APIModule(APIConstants.baseUrl))
                .build().inject(this)
        }

        try {
            syncForecasts()
            return Result.SUCCESS
        } catch (t: Throwable) {
            return Result.FAILURE
        }
    }

    private fun syncForecasts() {
        disposableObserver = object : DisposableObserver<List<Forecast>>() {
            override fun onComplete() {
            }

            override fun onNext(forecasts: List<Forecast>) {
                forecasts.forEach {
                    forecastRepository.getForecastFromApi(it.name!!)
                }
            }

            override fun onError(e: Throwable) {
                TODO("not implemented")
            }
        }
        forecastRepository.let {
            it!!.getSavedForecasts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(HomeViewModel.timeout, TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
        }

    }

}