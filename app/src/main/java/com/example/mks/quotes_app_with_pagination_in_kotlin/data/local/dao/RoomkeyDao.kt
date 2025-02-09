package com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Remote_keys_entity

@Dao
interface RoomkeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Remote_keys_entity>)

    @Query("Select * from Remote_keys_entity where id =:id")
    suspend fun getremotekeys(id:String):Remote_keys_entity








}