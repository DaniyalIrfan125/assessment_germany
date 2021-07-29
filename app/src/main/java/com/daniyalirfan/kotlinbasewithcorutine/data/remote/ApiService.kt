package com.daniyalirfan.kotlinbasewithcorutine.data.remote


import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogBreedsResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogImagesListResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("breeds")
    suspend fun getDogsBreedListApi(): Response<List<DogBreedsResponseModel>>

    @GET("images/search")
    suspend fun getDogImagesApi(
        @Query("breed_id") breed_id: Int,
        @Query("size") size: String = "small",
        @Query("limit") limit: Int = 100,
        @Query("page") page: Int = 0,
        @Query("format") format: String = "json",
    ): Response<List<DogImagesListResponseModel>>

}