package com.capstone.demokoling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ServiceAdapter(private val services: ArrayList<ServiceData>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(service: ServiceData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]

        Picasso.get().load(service.avatar).into(holder.avatarImageView)
        holder.nameTextView.text = service.name

        holder.itemView.setOnClickListener {
            listener.onItemClick(service)
        }
    }

    override fun getItemCount(): Int {
        return services.size
    }

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }
}
