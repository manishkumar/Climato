package kt.appsculture.com.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Forecast(
        //Changed to var for creating test data
        var date: Long,
        @SerializedName("main")
        var temperature: Temp,
        var weather: List<Condition>,
        var wind: Wind
)


data class Temp(
        //Changed to var for creating test data
        @SerializedName("temp_min")
        var min: Double,
        @SerializedName("temp_max")
        var max: Double,
        var pressure: Double,
        var humidity: Int
)

data class Condition(
        //Changed to var for creating test data
        var id: Int
)

data class Wind(
        //Changed to var for creating test data
        @SerializedName("speed")
        var speed: Double,
        @SerializedName("deg")
        var direction: Double
)