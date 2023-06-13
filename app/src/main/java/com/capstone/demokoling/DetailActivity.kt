package com.capstone.demokoling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<ResponseData>("data")
        if (data != null) {
            findViewById<TextView>(R.id.textLabel).text = data.KETERANGAN
            findViewById<TextView>(R.id.textId).text = "ID: ${data.ID}"
            findViewById<TextView>(R.id.textKeterangan).text = "Keterangan: ${data.KETERANGAN}"
            findViewById<TextView>(R.id.textLabelDetail).text = "Label: ${data.label}"
            findViewById<TextView>(R.id.textLongitude).text = "Longitude: ${data.longitude}"
            findViewById<TextView>(R.id.textLatitude).text = "Latitude: ${data.latitude}"
            findViewById<TextView>(R.id.textTlp).text = "TLP: ${data.TLP}"
        }
    }
}
