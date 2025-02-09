package com.example.mks.quotes_app_with_pagination_in_kotlin.data.di

import android.content.Context
import androidx.room.RoomDatabase
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.Imagesdatabase
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.Images_Dao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.local.dao.RoomkeyDao
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_Image_Entity_to_Domainimage
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.mapper.From_hit_to_Domain_Images
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.network.Images_api
import com.example.mks.quotes_app_with_pagination_in_kotlin.data.repositoroes_implementation.Image_Repositories_implementation
import com.example.mks.quotes_app_with_pagination_in_kotlin.domain.repositories.Images_Repositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Contract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    fun getImagesapi():Images_api{
     val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
     val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    return Retrofit.Builder().baseUrl("https://pixabay.com/").addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(Images_api::class.java)
    }

    @Provides
    @Singleton
    fun repoimplementation(imagesApi: Images_api,fromHitToDomainImages: From_hit_to_Domain_Images,roomkeyDao: RoomkeyDao,imagesDao: Images_Dao,fromImageEntityToDomainimage: From_Image_Entity_to_Domainimage):Images_Repositories{
        return Image_Repositories_implementation(
            imagesApi = imagesApi,
            fromHitToDomainImages = fromHitToDomainImages,
            roomkeyDao = roomkeyDao,
            imagesDao = imagesDao,
            fromImageEntityToDomainimage = fromImageEntityToDomainimage,
        )
    }


    @Provides
    @Singleton
    fun getimagesdatabase(@ApplicationContext context: Context):Imagesdatabase {
      return Imagesdatabase.getdatabase(context)
    }

    @Provides
    fun getImagesDao(imagesdatabase: Imagesdatabase):Images_Dao{
        return imagesdatabase.getImagesdao()
    }

    @Provides
    fun getRemotejeyDao(imagesdatabase: Imagesdatabase):RoomkeyDao{
        return imagesdatabase.getRemotekeydao()
    }




}