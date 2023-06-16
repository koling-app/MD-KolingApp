package com.capstone.demokoling

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class DetailResultActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed() // Kembali ke aktivitas sebelumnya
        }
        val data = intent.getParcelableExtra<ResponseData>("data")
        if (data != null) {

            findViewById<TextView>(R.id.textKeterangan).text = "Nama Layanan:\n${data.KETERANGAN}"

            Log.d("Data", "Longitude: ${data.longitude}")
            Log.d("Data", "Latitude: ${data.latitude}")

            val phoneNumber = data.TLP
            val buttonTlp = findViewById<Button>(R.id.buttonTlp)
            buttonTlp.text = "Telfon Sekarang"
            buttonTlp.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }

            val buttonMaps = findViewById<Button>(R.id.buttonMaps)
            buttonMaps.setOnClickListener {
                val latitude = data.latitude
                val longitude = data.longitude

                val uri = "google.navigation:q=$latitude,$longitude"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
            mapFragment.getMapAsync(this)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            getCurrentLocation(data)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val data = intent.getParcelableExtra<ResponseData>("data")
        if (data != null) {
            val location = LatLng(data.latitude, data.longitude)
            map.addMarker(MarkerOptions().position(location).title(data.label))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

            val geocoder = Geocoder(this, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(data.latitude, data.longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val fullAddress = address.getAddressLine(0)
                    findViewById<TextView>(R.id.textAlamat).text = "Alamat: \n$fullAddress"
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getCurrentLocation(data: ResponseData) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val destinationLocation = Location("destination")
                    destinationLocation.latitude = data.latitude
                    destinationLocation.longitude = data.longitude

                    val distanceInMeters = location.distanceTo(destinationLocation)
                    val distanceInKm = distanceInMeters / 1000

                    // Gunakan nilai distanceInKm untuk menampilkan jarak
                    findViewById<TextView>(R.id.textJarak).text = "Jarak: $distanceInKm km"
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}
