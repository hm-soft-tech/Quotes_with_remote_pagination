package com.example.mks.quotes_app_with_pagination_in_kotlin.hiltwithruntime_parameter

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel @AssistedInject constructor(
  @Assisted  private val  userrealname:String

)  : ViewModel(){

  val  username = MutableStateFlow("")

  init {
      username.update {
          userrealname
      }
  }


    @AssistedFactory
    interface Assestedviewmodel {
    fun create(userrealname: String):MainViewModel

    }






}