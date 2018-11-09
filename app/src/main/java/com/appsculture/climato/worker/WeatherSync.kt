package com.appsculture.climato.worker

import android.arch.lifecycle.LiveData
import androidx.work.*
import com.appsculture.climato.app.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherSync @Inject constructor(
    private val workManager: WorkManager,
    private val savedWorkStatus: LiveData<List<WorkStatus>>
) {


    fun start(intervalInMinutes: Long) {
        workManager.cancelAllWorkByTag(Constants.WORKER_TAG)

        val constraints = Constraints.Builder().setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val task =
            PeriodicWorkRequest.Builder(
                WeatherSyncWorker::class.java,
                intervalInMinutes,
                TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .addTag(Constants.WORKER_TAG)
                .build()
        workManager.enqueue(task)

    }

    fun status(): LiveData<List<WorkStatus>> {
        return savedWorkStatus
    }
}