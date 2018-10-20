package com.appsculture.climato.model

import com.google.gson.annotations.SerializedName

data class City(
    //Changed to var for creating test data
    var id: Integer,
    var name: String,
    @SerializedName("coord")
    var coordinates: Coordinates,
    var country: String
)

data class Coordinates(
    //Changed to var for creating test data
    @SerializedName("lon")
    var longitude: Double,
    @SerializedName("lat")
    var latitude: Double
)
