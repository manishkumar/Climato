package com.appsculture.climato.app

import android.app.Activity
import android.app.Application
import com.appsculture.climato.di.component.DaggerAppComponent
import com.appsculture.climato.di.modules.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ClimatoApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .aPIModule(APIModule(APIConstants.baseUrl))
            .storageModule(StorageModule())
            .syncModule(SyncModule())
            .viewModelModule(ViewModelModule())
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}