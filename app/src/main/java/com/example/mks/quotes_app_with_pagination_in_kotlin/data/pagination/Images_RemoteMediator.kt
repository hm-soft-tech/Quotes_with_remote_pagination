package com.example.mks.quotes_app_with_pagination_in_kotlin.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.Images_Dao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.RoomkeyDao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_Image_Entity_to_Domainimage
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_ImagesEntity
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.mapall
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Remote_keys_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images

@OptIn(ExperimentalPagingApi::class)
class Images_RemoteMediator(
      private val imagesApi: Images_api,
      private val roomkeyDao: RoomkeyDao,
      private val imagesDao: Images_Dao,
      private val q:String,
     ) : RemoteMediator<Int,Images_entity>(){

    override suspend fun initialize(): InitializeAction {
       val haslocaldata =  imagesDao.countimages(q)==0
        return if (haslocaldata){
             InitializeAction.LAUNCH_INITIAL_REFRESH
         }else{
               InitializeAction.SKIP_INITIAL_REFRESH
         }



    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Images_entity>): MediatorResult {

       val fromHitToImagesentity =From_hit_to_ImagesEntity(query = q)


     val page = when(loadType)
       {
           LoadType.REFRESH -> {
            val remotekey = state.anchorPosition?.let {
                state.closestItemToPosition(it)?.let {
                    roomkeyDao.getremotekeys(it.id)
                }
            }
           remotekey?.nextkey?.minus(1) ?:1


           }
           LoadType.PREPEND -> {

            val remotekey = state.pages?.firstOrNull {
              it.data.isNotEmpty()
            }?.data?.firstOrNull()?.let {
              roomkeyDao.getremotekeys(it.id)
            }
            remotekey?.prevkey ?:  return  MediatorResult.Success(remotekey!= null)



           }
           LoadType.APPEND -> {
            val remotekey = state.pages?.lastOrNull {
             it.data.isNotEmpty()
            }?.data?.lastOrNull()?.let {
                roomkeyDao.getremotekeys(it.id)
            }
             remotekey?.nextkey ?: return MediatorResult.Success(remotekey!=null)
           }
       }

       return try {
            val imagesreposne = imagesApi.getallimages(query = q , page = page)
            val images_from_api = imagesreposne.hits.distinctBy { it.id }
            val endofpaginationreached = imagesreposne.totalHits == page
            val prevkey =  if (page>1) page-1 else null
            val nextkey = if (endofpaginationreached) null else page+1



        val remotekeyentity =  images_from_api.map {
              Remote_keys_entity(
                  id = it.id.toString(),
                  prevkey = prevkey,
                  nextkey = nextkey
              )
           }

         roomkeyDao.insert(remotekeyentity)
         imagesDao.insertimagesdao(fromHitToImagesentity.mapall(images_from_api))

            MediatorResult.Success(endofpaginationreached)

        }
        catch (e:Exception){

            MediatorResult.Error(e)

        }



    }


}