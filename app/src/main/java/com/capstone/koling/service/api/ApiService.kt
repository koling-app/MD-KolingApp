package com.capstone.koling.service.api

import com.capstone.koling.service.response.LoginResponse
import com.capstone.koling.service.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

//    @GET("stories")
//    suspend fun getListStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int
//    ): Response<StoriesResponse>
//
//    @GET("stories")
//    fun getListStoriesWithLocation(
//        @Header("Authorization") token: String,
//        @Query("location") loc: Int = 1
//    ): Call<StoriesResponse>

//    @Multipart
//    @POST("stories")
//    fun postStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody
//    ): Call<AddStoryResponse>
}
