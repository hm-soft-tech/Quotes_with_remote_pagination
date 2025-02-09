package com.example.mks.quotes_app_with_pagination_in_kotlin

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.workmanagerinformation.Images_worker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application(),Configuration.Provider {

   @Inject lateinit var customworkerfactory: customworkerfactory

    override val workManagerConfiguration: Configuration
        get() =Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(customworkerfactory)
            .build()


}
class customworkerfactory @Inject constructor (private val imagesApi: Images_api): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
       return Images_worker(imagesApi,appContext,workerParameters)

    }


}