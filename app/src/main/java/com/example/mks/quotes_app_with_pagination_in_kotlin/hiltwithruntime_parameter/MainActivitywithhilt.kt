package com.example.mks.quotes_app_with_pagination_in_kotlin.hiltwithruntime_parameter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mks.quotes_app_with_pagination_in_kotlin.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivitywithhilt : AppCompatActivity() {

    @Inject
    lateinit var assestedinterface :MainViewModel.Assestedviewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_activitywithhilt)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

          val mainViewModel  = assestedinterface.create("hamza hello")
            lifecycleScope.launch(Dispatchers.Main) {
             mainViewModel.username.collectLatest {
             Toast.makeText(this@MainActivitywithhilt, it,Toast.LENGTH_LONG).show()
             }

         }

    }
}