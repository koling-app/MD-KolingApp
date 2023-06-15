// StoryAdapter.kt
package com.capstone.demokoling

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StoryAdapter(private val context: Context, private val storyList: MutableList<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storyList[position]
        holder.titleTextView.text = story.name
        holder.descriptionTextView.text = story.description

        Glide.with(context)
            .load(story.photoUrl)
            .placeholder(R.drawable.placeholder) // Placeholder gambar saat sedang di-load
            .error(R.drawable.error_image) // Gambar yang ditampilkan jika terjadi kesalahan
            .into(holder.imageView)

        holder.cardView.setOnClickListener {
            // Implementasikan tindakan saat cardView diklik di sini
        }
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    fun setData(newStoryList: List<Story>) {
        storyList.clear()
        storyList.addAll(newStoryList)
        notifyDataSetChanged()
    }
}
