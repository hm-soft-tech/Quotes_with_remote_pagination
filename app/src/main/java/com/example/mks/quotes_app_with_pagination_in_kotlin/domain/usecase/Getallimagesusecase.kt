package com.example.mks.quotes_app_with_pagination_in_kotlin.domain.usecase

import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.repositories.Images_Repositories
import javax.inject.Inject

class Getallimagesusecase @Inject constructor(private val imagesRepositories: Images_Repositories ) {


    operator  fun invoke(q:String) = imagesRepositories.getimages(q)




}