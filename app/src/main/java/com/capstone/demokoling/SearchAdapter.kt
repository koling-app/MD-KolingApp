package com.capstone.demokoling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter(
    private val services: List<ServiceData>,
    private val onItemClick: (ServiceData) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        holder.bind(service)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(service: ServiceData) {
            // Tampilkan data avatar dan nama dalam card view
            // Misalnya, menggunakan Glide untuk memuat gambar dari URL avatar
            Glide.with(itemView)
                .load(service.avatar)
                .into(avatarImageView)

            nameTextView.text = service.name

            itemView.setOnClickListener {
                onItemClick(service)
            }
        }
    }
}
