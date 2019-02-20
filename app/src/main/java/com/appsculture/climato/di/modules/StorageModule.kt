package com.appsculture.climato.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.appsculture.climato.app.DBConstants
import com.appsculture.climato.data.local.Database
import com.appsculture.climato.data.local.ForecastDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule() {

    @Provides
    @Singleton
    fun provideForecastsDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, DBConstants.name)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideForecastsDao(database: Database): ForecastDao = database.forecastsDao()

}