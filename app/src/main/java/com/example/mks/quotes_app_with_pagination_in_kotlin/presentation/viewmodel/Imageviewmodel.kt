package com.example.mks.quotes_app_with_pagination_in_kotlin.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.paging.map
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_Domain_Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.mapall
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Hit
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.repositoroes_implementation.Image_Repositories_implementation
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.usecase.Getallimagesusecase
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.usecase.Getimages_with_remotemediator_usecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Imageviewmodel @Inject constructor(private val  getallimagesusecase: Getallimagesusecase,
    private val getimagesWithRemotemediatorUsecase: Getimages_with_remotemediator_usecase
    )
   : ViewModel() {

   private  var query_string = MutableStateFlow("")


      fun update(query:String="red"){
      query_string.update { query }
    }


   val images = query_string.filter { it.isNotBlank() }
         .debounce(3000)
         .flatMapLatest { getimagesWithRemotemediatorUsecase.invoke(query_string.value) }
         .cachedIn(viewModelScope)




    /*
    val images = query_string.filter { it.isNotBlank() }
        .debounce(3000)
        .flatMapLatest { getimagesWithRemotemediatorUsecase.invoke(query_string.value)}
        .cachedIn(viewModelScope)

     */


















}