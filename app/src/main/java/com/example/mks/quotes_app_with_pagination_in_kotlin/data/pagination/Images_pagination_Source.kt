package com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_Domain_Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.mapall
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Hit
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import retrofit2.http.Query

class Images_pagination_Source(private val imagesApi: Images_api,
                               private val query: String,
                               private val fromHitToDomainImages: From_hit_to_Domain_Images

):PagingSource<Int,Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return  state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
       val key  = params.key?:1
       val pagesize = params.loadSize

   return  try {
           val reponse = imagesApi.getallimages(query = query, page = key)
             Log.d("info"," count occur :  ${reponse.total}")
              LoadResult.Page(
              data =     reponse.hits ,                      //fromHitToDomainImages.mapall(reponse.hits),
              prevKey = if (key==1) null else key.minus(1),
              nextKey = if (key == reponse.totalHits) null else key.plus(1)
              )

     }




     catch (e:Exception){
         LoadResult.Error(e)
     }

    }


}