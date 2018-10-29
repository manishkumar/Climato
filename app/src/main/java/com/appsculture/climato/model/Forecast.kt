package com.appsculture.climato.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "forecasts")
@Parcelize
data class Forecast(
    @Expose
    @PrimaryKey
    var id: Int?,
    @Ignore
    @Expose
    var cod: String?,
    @SerializedName("coord")
    @Expose
    var coordinate: Coordinate?,
    var country: String?,
    @SerializedName("dt")
    @Expose
    var date: Long?,
    @Expose
    var main: Main?,
    @Ignore
    @Expose
    var message: String?,
    @Expose
    var name: String?,
    @Expose
    var sys: Sys?,
    @Ignore
    @SerializedName("weather")
    @Expose
    var weathers: List<Weather>?,
    var weather: Weather?,
    @Expose
    var visibility: Int?
) : Parcelable {
    constructor() : this(0, "", null, "", 0, null, "", "", null, null, null, 0)
}

@Parcelize
data class Weather(
    @PrimaryKey
    @ColumnInfo(name = "weather_id")
    @Expose
    var id: Int?,
    @Expose
    var main: String?,
    @Expose
    var description: String?,
    @Expose
    var icon: String?
) : Parcelable {
    constructor() : this(0, "", "", "")
}

@Parcelize
data class Sys(
    @SerializedName("type")
    @Expose
    var type: Int?,
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "sys_id")
    @Expose
    var sys_id: Int?,
    var message: Double?,
    @Expose
    var country: String?,
    @Expose
    var sunrise: Long?,
    @Expose
    var sunset: Long?
) : Parcelable {
    constructor() : this(0, 0, 0.0, "", 0, 0)
}

@Parcelize
data class Main(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "main_id")
    var mainId: Long = 0,
    @SerializedName("temp")
    @Expose
    var temperature: Double?,
    @Expose
    var pressure: Double?,
    @Expose
    var humidity: Int?,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double?,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double?
) : Parcelable {
    constructor() : this(0, 0.0, 0.0, 0, 0.0, 0.0)
}

@Parcelize
data class Coordinate(
    @Expose
    var lat: Double?,
    @Expose
    var lon: Double?
) : Parcelable


