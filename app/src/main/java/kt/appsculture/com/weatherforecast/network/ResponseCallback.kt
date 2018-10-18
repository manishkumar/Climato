package kt.appsculture.com.weatherforecast.network

import kt.appsculture.com.weatherforecast.model.OpenWeatherMapResponse

interface ResponseCallback {
    fun onSuccess(response: OpenWeatherMapResponse)
    fun onFailure(error: String)
}
