package kt.appsculture.com.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class City(
    val id: Integer,
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinates,
    val country: String
)

data class Coordinates (
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double
)
