// StoryActivity.kt
package com.capstone.demokoling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryActivity : AppCompatActivity() {
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Tampilkan Main Activity
                    startActivity(Intent(this@StoryActivity, MainActivity::class.java))
                    true
                }
                R.id.navigation_services -> {
                    // Tampilkan Services Activity
                    startActivity(Intent(this@StoryActivity, ServicesActivity::class.java))
                    true
                }
                R.id.navigation_upload_story -> {
                    // Tampilkan Upload Story Activity
                    startActivity(Intent(this@StoryActivity, UploadStoryActivity::class.java))
                    true
                }
                R.id.navigation_view_story -> {
                    // Tampilkan View Story Activity
                    true
                }
                R.id.navigation_account_settings -> {
                    // Tampilkan Account Settings Activity
                    startActivity(Intent(this@StoryActivity, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set Story Activity sebagai halaman yang aktif secara default
        bottomNavigationView.selectedItemId = R.id.navigation_view_story

        // Inisialisasi RecyclerView dan Adapter
        storyRecyclerView = findViewById(R.id.recyclerView)
        storyAdapter = StoryAdapter(this, mutableListOf())

        // Set layout manager dan adapter untuk RecyclerView
        storyRecyclerView.layoutManager = LinearLayoutManager(this)
        storyRecyclerView.adapter = storyAdapter

        fetchStoryData()
    }

    private fun fetchStoryData() {
        val call = ApiClient.apiService.getAllStories()
        call.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    val storyResponse = response.body()
                    if (storyResponse != null) {
                        val storyData = storyResponse.data
                        showStoryData(storyData)
                    }
                } else {
                    Toast.makeText(this@StoryActivity, "Failed to fetch story data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Toast.makeText(this@StoryActivity, "Failed to fetch story data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showStoryData(storyData: List<Story>) {
        storyAdapter.setData(storyData)
    }
}
