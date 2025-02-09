package com.example.mks.quotes_app_with_pagination_in_kotlin.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mks.quotes_app_with_pagination_in_kotlin.R
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination.LoadAdapter
import com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.adapter.Imageadapter
import com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.interfaces_.Images_interface
import com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.viewmodel.Imageviewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     lateinit var  recyclerView: RecyclerView
     lateinit var query_text :EditText
     lateinit var progressBar: ProgressBar

     var imageurl:String =""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.image_list)
        query_text = findViewById(R.id.query)

        progressBar =findViewById(R.id.progress_circular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val imageadapter = Imageadapter(
            this,
            imagesInterface = object : Images_interface {
              override fun move_to_nextactivity(imageurl:String) {
                  startActivity(Intent(this@MainActivity,ImageActivity::class.java)
                      .putExtra("imageurl",imageurl)
                      )
                }
            },
        )
        val imagesViewmodel = ViewModelProvider(this).get(Imageviewmodel::class.java)

        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
         recyclerView.adapter = imageadapter.withLoadStateHeaderAndFooter(
          header = LoadAdapter(),
          footer = LoadAdapter()
        )
        recyclerView.hasFixedSize()

        GlobalScope.launch(Dispatchers.Main) {
            imagesViewmodel.images.collect(
                collector = {
                    progressBar.visibility=View.GONE
                    imageadapter.submitData(lifecycle = lifecycle, pagingData = it)
                    imageadapter.notifyDataSetChanged()
                }
            )
            /*
          imagesViewmodel.images.collectLatest {
              recyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)
              recyclerView.adapter = imageadapter
              imageadapter.submitData(lifecycle = lifecycle, pagingData = it)
              imageadapter.notifyDataSetChanged()
              recyclerView.hasFixedSize()}
           */
        }




        query_text.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progressBar.visibility=View.VISIBLE
                imagesViewmodel.update(query_text.text.toString())


                GlobalScope.launch(Dispatchers.Main) {
                    imagesViewmodel.images.collect(
                        collector = {
                            progressBar.visibility=View.GONE
                            imageadapter.submitData(lifecycle = lifecycle, pagingData = it)
                            imageadapter.notifyDataSetChanged()
                        }
                    )
                }
                query_text.text = null
                query_text.hideKeyboard()

                return@OnEditorActionListener true
            }
            false
        })

    }




}