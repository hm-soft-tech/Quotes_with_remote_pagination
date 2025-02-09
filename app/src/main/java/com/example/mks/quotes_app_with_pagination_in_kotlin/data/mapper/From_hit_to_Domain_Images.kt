package com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper

import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.Hit
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import javax.inject.Inject

class From_hit_to_Domain_Images @Inject constructor() : Mapping<Hit,Images> {
   
    override fun map(f: Hit): Images {
       return Images(
           id = f.id.toString(),
           imageurl = f.largeImageURL
       )
    }


}