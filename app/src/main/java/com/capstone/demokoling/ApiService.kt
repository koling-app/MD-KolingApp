package com.capstone.demokoling

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("predict")
    fun postData(
        @Body request: RequestBody
    ): Call<List<ResponseData>> // Update the return type to List<ResponseData>
}


