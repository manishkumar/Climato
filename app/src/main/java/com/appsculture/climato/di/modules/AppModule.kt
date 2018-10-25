package com.appsculture.climato.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.appsculture.climato.app.DBConstants
import com.appsculture.climato.data.local.Database
import com.appsculture.climato.data.local.ForecastDao
import com.appsculture.climato.module.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideForecastsDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, DBConstants.name)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideForecastsDao(database: Database): ForecastDao = database.forecastsDao()

    @Provides
    @Singleton
    fun provideHomeViewModelFactory(factory: HomeViewModelFactory): ViewModelProvider.Factory = factory


}