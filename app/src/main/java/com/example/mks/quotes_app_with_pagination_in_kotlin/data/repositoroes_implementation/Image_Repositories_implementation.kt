package com.example.mks.quotes_app_with_pagination_in_kotlin.data.repositoroes_implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.Images_Dao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.RoomkeyDao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_Image_Entity_to_Domainimage
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_Domain_Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination.Images_RemoteMediator
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination.PagingSource
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.repositories.Images_Repositories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Image_Repositories_implementation @Inject constructor(
    private val imagesApi: Images_api,
    private val fromHitToDomainImages: From_hit_to_Domain_Images,
    private val roomkeyDao: RoomkeyDao,
    private val imagesDao: Images_Dao,
    private val fromImageEntityToDomainimage: From_Image_Entity_to_Domainimage
    )  : Images_Repositories{


    override  fun getimages(q: String): Pager<Int, Images> {

      return Pager(
          config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 1,
            enablePlaceholders = false,
             initialLoadSize = 10

          ),
           pagingSourceFactory = {
               PagingSource(
                   imagesApi,
                   query = q,
                   fromHitToDomainImages
               )


          }
      )
       /*
       val data  =imagesApi.getallimages(query = q, page = 1)
        return data.hits
        */

    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getImagesfromroomDatabase(query: String): Flow<PagingData<Images>> {
       return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
               imagesDao.getallimages(query)
            },
            remoteMediator = Images_RemoteMediator(
                imagesApi = imagesApi,
                roomkeyDao =  roomkeyDao,
                imagesDao = imagesDao,
                q = query
            )
        ).flow.map {
            it.map { 
                fromImageEntityToDomainimage.map(it)
            }
       }

    }


}