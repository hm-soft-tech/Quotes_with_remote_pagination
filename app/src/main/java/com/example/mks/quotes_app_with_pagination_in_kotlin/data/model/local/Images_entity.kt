package com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Images_entity(
  @PrimaryKey(autoGenerate = false)
  val id:String,
  val image_url:String,
  val query:String

)


