package com.appsculture.climato.data.remote

import com.appsculture.climato.app.APIConstants
import com.appsculture.climato.model.Forecast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("weather/")
    fun getWeatherData(
        @Query(value = "q") query: String,
        @Query(value = "appId") appID: String = APIConstants.apiKey
    ): Observable<Forecast>

}