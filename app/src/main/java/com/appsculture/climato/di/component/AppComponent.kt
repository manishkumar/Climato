package com.appsculture.climato.di.component

import com.appsculture.climato.app.ClimatoApplication
import com.appsculture.climato.di.modules.APIModule
import com.appsculture.climato.di.modules.AppModule
import com.appsculture.climato.di.modules.BuildersModule
import com.appsculture.climato.utils.BackgroundSyncWeather
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class, BuildersModule::class, AppModule::class,
        APIModule::class
    )
)
interface AppComponent {
    fun inject(app: ClimatoApplication)

    fun inject(syncWorker: BackgroundSyncWeather)

}