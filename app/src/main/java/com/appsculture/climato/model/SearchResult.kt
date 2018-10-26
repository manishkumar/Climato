package com.appsculture.climato.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


data class SearchResult(
    @Expose
    var cod: String,
    @Expose
    var message: String,
    @Expose
    var cnt: Int,
    @Expose
    var list: List<Forecast>,
    @Expose
    var city: City
)

@Entity(tableName = "city")
@Parcelize
data class City(
    @ColumnInfo(name = "city_id")
    @Expose
    var id: Int,
    @Expose
    var name: String,
    @Expose
    var country: String,
    @ColumnInfo(name = "coordinate")
    @Expose
    var coord: Coordinate
) : Parcelable

@Parcelize
data class Coordinate(
    @Expose
    var lat: Double?,
    @Expose
    var lon: Double?
) : Parcelable