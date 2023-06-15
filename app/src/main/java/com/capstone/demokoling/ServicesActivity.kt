package com.capstone.demokoling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServicesActivity : AppCompatActivity() {
        // RecyclerView adapter
        inner class CardViewAdapter(private val cardList: List<CardItem>) :
            RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
                val cardText: TextView = itemView.findViewById(R.id.cardText)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cardview, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val cardItem = cardList[position]

                holder.cardImage.setImageResource(cardItem.imageResource)
                holder.cardText.text = cardItem.text

                holder.itemView.setOnClickListener {
                    val selectedActivity = cardItem.activity

                    // Tampilkan aktivitas yang dipilih
                    startActivity(Intent(this@ServicesActivity, selectedActivity))
                }
            }

            override fun getItemCount(): Int {
                return cardList.size
            }
        }

        data class CardItem(val imageResource: Int, val text: String, val activity: Class<*>)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Tampilkan Main Activity
                    startActivity(Intent(this@ServicesActivity, MainActivity::class.java))
                    true
                }
                R.id.navigation_services -> {
                    // Tampilkan Services Activity
                    true
                }
                R.id.navigation_upload_story -> {
                    // Tampilkan Upload Story Activity
                    startActivity(Intent(this@ServicesActivity, UploadStoryActivity::class.java))
                    true
                }
                R.id.navigation_view_story -> {
                    // Tampilkan View Story Activity
                    startActivity(Intent(this@ServicesActivity, StoryActivity::class.java))
                    true
                }
                R.id.navigation_account_settings -> {
                    // Tampilkan Account Settings Activity
                    startActivity(Intent(this@ServicesActivity, SettingActivity::class.java))
                    true
                }
                else -> false
            }

        }
        val servicesRecyclerView = findViewById<RecyclerView>(R.id.servicesRecyclerView)

        val cardList = listOf(
            CardItem(R.drawable.police_image, "Polisi", PolisiActivity::class.java),
            CardItem(R.drawable.rs_image, "Rumah Sakit", RSActivity::class.java)
        )

        val adapter = CardViewAdapter(cardList)
        servicesRecyclerView.adapter = adapter
        servicesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set Services Activity sebagai halaman yang aktif secara default
        bottomNavigationView.selectedItemId = R.id.navigation_services
    }


    }