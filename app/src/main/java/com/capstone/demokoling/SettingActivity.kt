package com.capstone.demokoling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Tampilkan Main Activity
                    startActivity(Intent(this@SettingActivity, MainActivity::class.java))
                    true
                }
                R.id.navigation_services -> {
                    // Tampilkan Services Activity
                    startActivity(Intent(this@SettingActivity, ServicesActivity::class.java))
                    true
                }
                R.id.navigation_upload_story -> {
                    // Tampilkan Upload Story Activity
                    startActivity(Intent(this@SettingActivity, UploadStoryActivity::class.java))
                    true
                }
                R.id.navigation_view_story -> {
                    // Tampilkan View Story Activity
                    startActivity(Intent(this@SettingActivity, StoryActivity::class.java))
                    true
                }
                R.id.navigation_account_settings -> {
                    // Tampilkan Account Settings Activity
                    true
                }
                else -> false
            }
        }

        // Set Setting Activity sebagai halaman yang aktif secara default
        bottomNavigationView.selectedItemId = R.id.navigation_account_settings

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Menghapus data login dari SharedPreferences
            SharedPreferencesUtil.clearLoginData(this)

            // Kembali ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Menutup Activity saat ini agar tidak bisa dikembalikan dengan tombol back
        }

    }
}
