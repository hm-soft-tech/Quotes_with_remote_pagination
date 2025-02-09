package com.example.mks.quotes_app_with_pagination_in_kotlin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.Images_Dao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.RoomkeyDao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Remote_keys_entity

@Database(entities = [Images_entity::class,Remote_keys_entity::class], version = 1)
abstract class Imagesdatabase :RoomDatabase() {

   companion object {
         fun getdatabase(context: Context):Imagesdatabase{
          return Room.databaseBuilder(
            context,
            klass = Imagesdatabase::class.java,
            name = "Images_db",
        ).build()
       }

   }

   abstract fun getImagesdao():Images_Dao
   abstract fun getRemotekeydao():RoomkeyDao




}