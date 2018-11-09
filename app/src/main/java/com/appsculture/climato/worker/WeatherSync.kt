package com.appsculture.climato.worker

import android.arch.lifecycle.LiveData
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherSync @Inject constructor(
    private val workManager: WorkManager,
    private val savedWorkStatus: LiveData<List<WorkStatus>>
) {

    fun start(intervalInMinutes: Long) {
        val constraints = Constraints.Builder().setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val task =
            PeriodicWorkRequest.Builder(
                WeatherSyncWorker::class.java,
                intervalInMinutes,
                TimeUnit.MINUTES
            ).setConstraints(constraints).build()
        workManager.enqueue(task)
    }

    fun status(): LiveData<List<WorkStatus>> {
        return savedWorkStatus
    }
}