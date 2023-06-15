package com.capstone.demokoling

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

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

            val longitudeTextView = findViewById<TextView>(R.id.textLongitude)
            val latitudeTextView = findViewById<TextView>(R.id.textLatitude)

            Log.d("Data", "Longitude: ${data.longitude}")
            Log.d("Data", "Latitude: ${data.latitude}")

            longitudeTextView.text = "Longitude: ${data.longitude}"
            latitudeTextView.text = "Latitude: ${data.latitude}"

            val buttonTlp = findViewById<Button>(R.id.buttonTlp)
            buttonTlp.text = "TLP: ${data.TLP}"
            buttonTlp.setOnClickListener {
                val phoneNumber = data.TLP
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val data = intent.getParcelableExtra<ResponseData>("data")
        if (data != null) {
            val location = LatLng(data.latitude, data.longitude)
            map.addMarker(MarkerOptions().position(location).title(data.label))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }
}
