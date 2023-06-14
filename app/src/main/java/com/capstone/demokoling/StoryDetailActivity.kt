package com.capstone.demokoling

// Import statements
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.demokoling.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data cerita dari Intent
        val story = intent.getParcelableExtra<Story>("STORY_EXTRA")

        if (story != null) {
            // Menampilkan data cerita di layout
            binding.titleTextView.text = story.name
            binding.descriptionTextView.text = story.description
            // Menggunakan library Glide untuk memuat gambar dari URL
            Glide.with(this)
                .load(story.photoUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.imageView)
        } else {
            // Data cerita tidak tersedia, bisa ditangani sesuai kebutuhan
        }
    }
}
