package com.capstone.demokoling

import android.content.Intent
import android.util.Log
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
        Log.d("Adapter Data Size", "Size: ${dataList.size}")
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textLabel: TextView = itemView.findViewById(R.id.textLabel)

        fun bind(data: ResponseData) {
            textLabel.text = data.KETERANGAN

            itemView.setOnClickListener {
                // Handle card click event here
                // You can open a new activity or show a dialog to display detailed information
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("data", data)
                itemView.context.startActivity(intent)
            }
        }
    }
}
