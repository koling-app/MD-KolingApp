package com.capstone.demokoling

import android.app.Service
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ServiceDetailActivity : AppCompatActivity() {
    private val services: MutableList<ServiceData> = mutableListOf()
    private var onItemClickListener: ((ServiceData) -> Unit)? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        val service = intent.getParcelableExtra<ServiceData>("service")

        // Use the service object to display the details
        val textName = findViewById<TextView>(R.id.textName)
        val textPhone = findViewById<TextView>(R.id.textPhone)
        val textLocation = findViewById<TextView>(R.id.textLocation)

        textName.text = service?.name
        textPhone.text = service?.phone
        textLocation.text = "Lat: ${service?.lat}, Lon: ${service?.lon}"
    }
}

