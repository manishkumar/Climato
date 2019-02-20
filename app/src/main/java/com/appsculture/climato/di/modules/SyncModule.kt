package com.appsculture.climato.di.modules

import android.arch.lifecycle.LiveData
import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkStatus
import com.appsculture.climato.app.Constants
import com.appsculture.climato.utils.NotificationProvider
import com.appsculture.climato.worker.WeatherSync
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SyncModule() {

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
    fun provideNotificationProvider(context: Context): NotificationProvider {
        return NotificationProvider(context)
    }

}