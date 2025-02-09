package com.example.mks.quotes_app_with_pagination_in_kotlin.workmanagerinformation

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mks.quotes_app_with_pagination_in_kotlin.R
import kotlin.time.Duration

class WorkerActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_worker)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
      val builder = Data.Builder()
        builder.putString("query","red")

      val oneTimeWorkRequest = OneTimeWorkRequestBuilder<Images_worker>()
          .setInitialDelay(java.time.Duration.ofSeconds(10))
          .setInputData(builder.build())
          .setBackoffCriteria(BackoffPolicy.LINEAR,java.time.Duration.ofSeconds(10))
          .build()
        val workManager = WorkManager.getInstance(this)
               workManager.enqueue(oneTimeWorkRequest)
         workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
             if (it?.state?.isFinished!!)
             {
             Toast.makeText(this@WorkerActivity,"${it.outputData.getInt("outputdata",0)}",Toast.LENGTH_LONG).show()
             }
         })
        



    }
}