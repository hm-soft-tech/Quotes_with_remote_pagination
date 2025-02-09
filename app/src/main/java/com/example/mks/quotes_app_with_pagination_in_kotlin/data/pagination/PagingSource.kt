package com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_Domain_Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.mapall
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images

class PagingSource(
    private val imagesApi: Images_api,
    private val query:String,
    private val fromHitToDomainImages: From_hit_to_Domain_Images
           ) : PagingSource<Int, Images>() {

    override fun getRefreshKey(state: PagingState<Int, Images>): Int? {
      return  state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images> {
       val pagenumer = params.key ?: 1
       val pagesize = params.loadSize

     return  try {
          val  images = imagesApi.getallimages(query = query, page = pagenumer)
               LoadResult.Page(
                   data = fromHitToDomainImages.mapall(images.hits),
                   prevKey = if (pagenumer == 1) null else pagenumer.minus(1),
                   nextKey = if (images.hits.size < pagesize) null else pagenumer.plus(1)
               )

      }
      catch (e:Exception){
          LoadResult.Error(e)
      }
    }


}