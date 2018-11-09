package com.appsculture.climato.di.modules

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkStatus
import com.appsculture.climato.app.Constants
import com.appsculture.climato.app.DBConstants
import com.appsculture.climato.data.local.Database
import com.appsculture.climato.data.local.ForecastDao
import com.appsculture.climato.data.local.prefs.PreferenceHelper
import com.appsculture.climato.module.home.HomeViewModelFactory
import com.appsculture.climato.utils.NotificationProvider
import com.appsculture.climato.utils.WeatherDataFormatter
import com.appsculture.climato.worker.WeatherSync
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }

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
    fun provideHomeViewModelFactory(factory: HomeViewModelFactory): ViewModelProvider.Factory =
        factory


    @Provides
    @Singleton
    fun provideWeatherFormatter(context: Context): WeatherDataFormatter =
        WeatherDataFormatter(context)

    @Provides
    @Singleton
    fun provideWorkManager(): WorkManager {
        return WorkManager.getInstance()
    }

    @Provides
    fun provideWorkManagerStatus(workManager: WorkManager): LiveData<List<WorkStatus>> {
        return workManager.getStatusesForUniqueWorkLiveData(Constants.WORKER_TAG)
    }

    @Provides
    fun provideWeatherSync(
        workManager: WorkManager,
        workStatus: LiveData<List<WorkStatus>>
    ): WeatherSync {
        return WeatherSync(workManager, workStatus)
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }

    @Provides
    @Singleton
    fun provideNotificationProvider(context: Context): NotificationProvider {
        return NotificationProvider(context)
    }

}