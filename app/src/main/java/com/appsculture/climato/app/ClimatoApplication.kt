package com.appsculture.climato.app

import android.app.Activity
import android.app.Application
import com.appsculture.climato.data.local.prefs.PreferenceHelper
import com.appsculture.climato.di.component.DaggerAppComponent
import com.appsculture.climato.di.modules.APIModule
import com.appsculture.climato.di.modules.AppModule
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
            .build().inject(this)

        ClimatoApplication.preferenceHelper = PreferenceHelper(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    companion object {
        lateinit var preferenceHelper : PreferenceHelper
        private set
    }

}