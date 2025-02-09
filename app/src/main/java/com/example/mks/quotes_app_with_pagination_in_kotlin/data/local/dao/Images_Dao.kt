package com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import kotlinx.coroutines.flow.Flow

@Dao
interface Images_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertimagesdao(imagesEntity: List<Images_entity>)

    @Query("Select * from Images_entity where `query` =:q")
    fun getallimages(q: String):PagingSource<Int,Images_entity>

    @Query(" Select Count(*) from Images_Entity where `query`=:q")
    suspend  fun countimages(q:String):Int






}