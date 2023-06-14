package com.capstone.demokoling

import android.app.Service
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ServicesAdapter : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    private val services: MutableList<ServiceData> = mutableListOf()
    private var onItemClickListener: ((ServiceData) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.textName)
        private val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
        private val textPhone: TextView = itemView.findViewById(R.id.textPhone)

        fun bind(service: ServiceData) {
            textName.text = service.name
            textPhone.text = service.phone

            // Load the avatar image using a library like Picasso or Glide
            // Example with Picasso:
            Picasso.get().load(service.avatar).into(imageAvatar)

            // Set click listener for the card view item
            itemView.setOnClickListener {
                onItemClickListener?.invoke(service)
            }
        }
    }

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

    fun setServices(serviceList: List<ServiceData>) {
        services.clear()
        services.addAll(serviceList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (ServiceData) -> Unit) {
        onItemClickListener = listener
    }
}
