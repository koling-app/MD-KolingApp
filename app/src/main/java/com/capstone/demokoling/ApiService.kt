package com.capstone.demokoling

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("predict")
    fun postData(
        @Body request: RequestBody
    ): Call<List<ResponseData>>

    @Headers("Content-Type: application/json")
    @POST("teks")
    fun postDataTeks(
        @Body request: RequestBodyText
    ): Call<List<ResponseData>>

    @GET("story")
    fun getAllStories(): Call<StoryResponse>

    @GET("services")
    fun getServices(): Call<ServicesResponse>

    @POST("story")
    fun uploadStory(
        @Body request: UploadStoryRequest
    ): Call<UploadStoryResponse>

    @GET("users")
    fun getUsers(): Call<UserResponse>


}


