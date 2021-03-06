package com.appsculture.climato.di.component

import com.appsculture.climato.app.ClimatoApplication
import com.appsculture.climato.di.modules.*
import com.appsculture.climato.worker.WeatherSyncWorker
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        BuildersModule::class,
        AppModule::class,
        APIModule::class,
        StorageModule::class,
        SyncModule::class,
        ViewModelModule::class
    )
)
interface AppComponent {
    fun inject(app: ClimatoApplication)

    fun inject(syncWorker: WeatherSyncWorker)

}