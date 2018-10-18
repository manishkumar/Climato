package kt.appsculture.com.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    var date: Long,
    @SerializedName("main")
    val temperature: Temp,
    val weather: List<Condition>,
    val wind: Wind
)


data class Temp(
    @SerializedName("temp_min")
    val min: Double,
    @SerializedName("temp_max")
    val max: Double,
    val pressure: Double,
    val humidity: Int
)

data class Condition(
    val id: Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val direction: Double
)