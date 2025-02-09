package com.example.mks.quotes_app_with_pagination_in_kotlin.data.network

import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Image_response
import retrofit2.http.GET
import retrofit2.http.Query

interface Images_api {

   // https://pixabay.com/api/?key=48482666-148abc51a15366bdea9b00544&q=yellow+flowers&image_type=photo

  @GET("api/")
  suspend fun getallimages(@Query("key") key:String ="48482666-148abc51a15366bdea9b00544",
                           @Query("q") query:String="yellow",
                           @Query("page") page:Int
                           ):Image_response


}