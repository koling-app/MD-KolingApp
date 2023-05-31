package com.capstone.koling

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.capstone.koling.service.api.ApiService
import com.capstone.koling.service.response.LoginResponse
import com.capstone.koling.service.response.RegisterResponse
import com.capstone.koling.utils.Event
import com.capstone.koling.SessionPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupportRepository private constructor(
    private val pref: SessionPreferences,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

//    private val _uploadResponse = MutableLiveData<AddStoryResponse>()
//    val uploadResponse: LiveData<AddStoryResponse> = _uploadResponse
//
//    private val _list = MutableLiveData<StoriesResponse>()
//    val list: LiveData<StoriesResponse> = _list

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun postRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postRegister(name, email, password)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postLogin(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

//    fun getStories(): LiveData<PagingData<ListStoryItem>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                StoryPagingSource(pref, apiService)
//            }
//        ).liveData
//    }

//    fun getListStoriesWithLocation(token: String) {
//        _isLoading.value = true
//        val client = apiService.getListStoriesWithLocation(token)
//
//        client.enqueue(object : Callback<StoriesResponse> {
//            override fun onResponse(
//                call: Call<StoriesResponse>,
//                response: Response<StoriesResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body() != null) {
//                    _list.value = response.body()
//                } else {
//                    _toastText.value = Event(response.message().toString())
//                    Log.e(
//                        TAG,
//                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
//                _toastText.value = Event(t.message.toString())
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

//    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
//        _isLoading.value = true
//        val client = apiService.postStory(token, file, description)
//
//        client.enqueue(object : Callback<AddStoryResponse> {
//            override fun onResponse(
//                call: Call<AddStoryResponse>,
//                response: Response<AddStoryResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body() != null) {
//                    _uploadResponse.value = response.body()
//                    _toastText.value = Event(response.body()?.message.toString())
//                } else {
//                    _toastText.value = Event(response.message().toString())
//                    Log.e(
//                        TAG,
//                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
//                Log.d("error upload", t.message.toString())
//            }
//
//        }
//        )
//    }

    fun getSession(): LiveData<SessionModel> {
        return pref.getSession().asLiveData()
    }

    suspend fun saveSession(session: SessionModel) {
        pref.saveSession(session)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        private const val TAG = "StoryRepository"

        @Volatile
        private var instance: SupportRepository? = null
        fun getInstance(
            preferences: SessionPreferences,
            apiService: ApiService
        ): SupportRepository =
            instance ?: synchronized(this) {
                instance ?: SupportRepository(preferences, apiService)
            }.also { instance = it }
    }
}