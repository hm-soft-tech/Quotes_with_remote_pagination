package com.example.mks.quotes_app_with_pagination_in_kotlin.workmanagerinformation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

// we also have a coroutineworker for suspended function
@HiltWorker
class Images_worker @AssistedInject constructor (
    @Assisted private val images_api : Images_api,
    @Assisted   context: Context,
    @Assisted  workerParams:WorkerParameters,) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
    return  try {
        val query  = inputData.getString("query")
        val reponse =  images_api.getallimages(query = "red",page = 1)
         if (reponse.hits.size>0)
         {
            Log.d("data",reponse.hits.size.toString())
             Result.success(workDataOf("outputdata" to reponse.hits.get(1).imageSize))
         }
        else{
             Log.d("data","nothing found")
             Result.retry()
         }

     }
     catch (e:Exception){

        Result.failure(Data.Builder().putString("error",e.message.toString()).build())
     }


    }


}