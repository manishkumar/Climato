package com.appsculture.climato.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.appsculture.climato.model.Forecast

@Database(entities = arrayOf(Forecast::class), version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun forecastsDao(): ForecastDao
}