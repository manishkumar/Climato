package com.appsculture.climato.di.modules

import android.app.Application
import android.content.Context
import com.appsculture.climato.data.local.prefs.PreferenceHelper
import com.appsculture.climato.utils.WeatherDataFormatter
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
    fun provideWeatherFormatter(context: Context): WeatherDataFormatter =
        WeatherDataFormatter(context)

    @Provides
    @Singleton
    fun providePreferenceHelper(context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }
}