package com.capstone.demokoling

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

//    @Headers("Content-Type: application/json")
//    @POST("predict")
//    fun postData(
//        @Body request: RequestBody
//    ): Call<List<ResponseData>>

    @Headers("Content-Type: application/json")
    @POST("calculate")
    fun postData(
        @Body request: RequestBody
    ): Call<List<ResponseData>>

    @Headers("Content-Type: application/json")
    @POST("predict")
    fun postDataTeks(
        @Body request: RequestBodyText
    ): Call<List<ResponseData>>

    @GET("story")
    fun getAllStories(): Call<StoryResponse>

    @GET("services")
    fun getServices(): Call<ServicesResponse>

//    @Multipart
//    @POST("story")
//    fun uploadStory(
//        @Part("name") name: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part photoUrl: MultipartBody.Part,
//        @Part("latitude") latitude: RequestBody,
//        @Part("longitude") longitude: RequestBody
//    ): Call<UploadStoryResponse>
@POST("story")
fun uploadStory(
    @Body requestBody: MultipartBody.Part,
    @Part photoUrl: String,
    @Part("latitude") latitude: RequestBody,
    @Part("longitude") longitude: RequestBody
): Call<UploadStoryResponse>




    @GET("users")
    fun getUsers(): Call<UserResponse>

    @GET("service/{name}")
    fun searchService(
        @Path("name") name: String
    ): Call<ServicesResponse>

    @Multipart
    @POST("story")
    abstract fun uploadStory(
        @Part requestBody: MultipartBody.Part,
        @Part("photoUrl") photoUrl: String
    ): Call<UploadStoryResponse>


    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("service/posko")
    fun searchPosko(@Query("query") query: String): Call<ApiResponse>

}


