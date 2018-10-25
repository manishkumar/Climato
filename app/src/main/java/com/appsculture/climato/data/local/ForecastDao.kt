package com.appsculture.climato.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.appsculture.climato.model.Forecast
import io.reactivex.Single

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecasts")
    fun forecasts(): Single<List<Forecast>>

    @Query("SELECT * FROM forecasts WHERE id = :id LIMIT 1")
    fun forecast(id: Int): Single<Forecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: Forecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecasts(forecasts: List<Forecast>)

    @Query("DELETE from forecasts")
    fun deleteAll()
}