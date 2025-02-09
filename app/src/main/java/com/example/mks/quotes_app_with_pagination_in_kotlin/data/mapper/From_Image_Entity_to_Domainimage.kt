package com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper

import com.example.mks.quotes_app_with_pagination_in_kotlin.data.model.local.Images_entity
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.model.Images
import javax.inject.Inject

class From_Image_Entity_to_Domainimage @Inject constructor() : Mapping<Images_entity,Images> {
    override fun map(f: Images_entity): Images {
       return Images(
           id = f.id,
           imageurl = f.image_url,
       )
    }


}