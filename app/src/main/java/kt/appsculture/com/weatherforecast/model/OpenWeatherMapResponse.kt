package kt.appsculture.com.weatherforecast.model

import android.content.ContentValues
import com.google.gson.annotations.SerializedName

data class OpenWeatherMapResponse(
    val city: City,
    @SerializedName("cod")
    val messageCode: Int,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("list")
    val forecasts: List<Forecast>
)