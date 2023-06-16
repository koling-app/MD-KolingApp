package com.capstone.demokoling

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultAdapter(private val searchResults: List<Posko>) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchResult = searchResults[position]
        holder.bind(searchResult)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)

        fun bind(searchResult: Posko) {
            nameTextView.text = searchResult.name
            // Load the avatar image using your preferred image loading library
            // Glide example:
            // Glide.with(itemView.context).load(searchResult.avatar).into(avatarImageView)

            itemView.setOnClickListener {
                // Handle item click, open detail activity, etc.
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("posko", searchResult)
                itemView.context.startActivity(intent)
            }
        }
    }
}
