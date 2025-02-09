package com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper

import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Hit
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity

class From_hit_to_ImagesEntity(private val query: String) :Mapping<Hit,Images_entity> {



    override fun map(f: Hit): Images_entity {
     return Images_entity(
         id = f.id.toString(),
         image_url = f.largeImageURL,
         query = query
     )
    }


}