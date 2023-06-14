package com.capstone.demokoling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class UploadStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_story)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Tampilkan Main Activity
                    startActivity(Intent(this@UploadStoryActivity, MainActivity::class.java))
                    true
                }
                R.id.navigation_services -> {
                    // Tampilkan Services Activity
                    startActivity(Intent(this@UploadStoryActivity, ServicesActivity::class.java))
                    true
                }
                R.id.navigation_upload_story -> {
                    // Tampilkan Upload Story Activity
                    true
                }
                R.id.navigation_view_story -> {
                    // Tampilkan View Story Activity
                    startActivity(Intent(this@UploadStoryActivity, StoryActivity::class.java))
                    true
                }
                R.id.navigation_account_settings -> {
                    // Tampilkan Account Settings Activity
                    startActivity(Intent(this@UploadStoryActivity, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set Upload Story Activity sebagai halaman yang aktif secara default
        bottomNavigationView.selectedItemId = R.id.navigation_upload_story
    }
}
