package com.capstone.demokoling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.demokoling.ApiClient.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve search results from API (using the provided searchPosko function) and set up the adapter
        val query = intent.getStringExtra("query")
        query?.let {
            searchPosko(it)
        }
    }

    private fun searchPosko(query: String) {
        val call: Call<ApiResponse> = apiService.searchPosko(query)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val poskoList: List<Posko>? = response.body()?.data
                    poskoList?.let {
                        searchResultAdapter = SearchResultAdapter(it)
                        recyclerView.adapter = searchResultAdapter
                    }
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle network failure
            }
        })
    }
}
