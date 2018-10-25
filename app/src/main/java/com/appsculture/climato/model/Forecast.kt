package com.appsculture.climato.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "forecasts")
data class Forecast(
    @PrimaryKey
    var id: Int?,
    var name: String?,
    @Ignore
    @SerializedName("weather")
    @Expose
    var weathers: List<Weather>?,
    var weather: Weather?,
    @Expose
    var main: Main?,
    @Expose
    var visibility: Int?,
    @SerializedName("dt")
    @Expose
    var date: Long?,
    @Expose
    var sys: Sys?
) {
    constructor() : this(0, "", null, null, null, 0, 0, null)
}

/*@Entity(
    tableName = "weather",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Forecast::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onDelete = CASCADE
        )
    )
)*/
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
) {
    constructor() : this(0, "", "", "")
}


//@Entity(tableName = "sys")
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
) {
    constructor() : this(0, 0, 0.0, "", 0, 0)
}

//@Entity(tableName = "main")
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
) {
    constructor() : this(0, 0.0, 0.0, 0, 0.0, 0.0)
}


