package com.capstone.demokoling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.demokoling.R
import com.capstone.demokoling.ResponseData

class ResponseDataAdapter(private var dataList: List<ResponseData>) :
    RecyclerView.Adapter<ResponseDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_response_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(newDataList: List<ResponseData>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textLabel: TextView = itemView.findViewById(R.id.textLabel)

        fun bind(data: ResponseData) {
            textLabel.text = data.KETERANGAN
            // Bind other data fields to their respective TextViews or UI elements
        }
    }
}
