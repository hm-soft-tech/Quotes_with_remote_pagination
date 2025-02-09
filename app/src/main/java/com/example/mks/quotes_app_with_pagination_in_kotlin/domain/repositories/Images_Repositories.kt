package com.example.mks.quotes_app_with_pagination_in_kotlin.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Hit
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import kotlinx.coroutines.flow.Flow

interface Images_Repositories {


      fun getimages(q:String):Pager<Int,Images>

      fun getImagesfromroomDatabase(query:String):Flow<PagingData<Images>>


  //   fun getallimages(q:String):Pager<Int,Images>



}