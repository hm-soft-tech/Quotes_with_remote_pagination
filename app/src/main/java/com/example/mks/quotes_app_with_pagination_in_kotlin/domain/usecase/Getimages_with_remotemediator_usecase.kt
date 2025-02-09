package com.example.mks.quotes_app_with_pagination_in_kotlin.domain.usecase

import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.repositories.Images_Repositories
import javax.inject.Inject

class Getimages_with_remotemediator_usecase @Inject constructor(private val imagesRepositories: Images_Repositories)  {


    operator fun invoke(query:String) = imagesRepositories.getImagesfromroomDatabase(query)



}