package com.capstone.demokoling

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import android.location.Address
import android.location.Geocoder

import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val RESULT_SPEECH = 1
        private const val ID_BahasaIndonesia = "id"
    }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var tvText: TextView
    private lateinit var btnSpeak: ImageButton
    private lateinit var textToSpeech: TextToSpeech

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val icon1 = findViewById<ImageView>(R.id.icon1)
        val icon2 = findViewById<ImageView>(R.id.icon2)
        val icon3 = findViewById<ImageView>(R.id.icon3)
        val icon4 = findViewById<ImageView>(R.id.icon4)
        val icon5 = findViewById<ImageView>(R.id.icon5)
        val icon6 = findViewById<ImageView>(R.id.icon6)

        val icon1Text = findViewById<TextView>(R.id.icon1_text)
        val icon2Text = findViewById<TextView>(R.id.icon2_text)
        val icon3Text = findViewById<TextView>(R.id.icon3_text)
        val icon4Text = findViewById<TextView>(R.id.icon4_text)
        val icon5Text = findViewById<TextView>(R.id.icon5_text)
        val icon6Text = findViewById<TextView>(R.id.icon6_text)

        // Mengatur listener untuk SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Aksi yang diambil saat pengguna menekan tombol 'Enter' di keyboard setelah memasukkan teks pencarian
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Aksi yang diambil saat teks pencarian berubah
                return false
            }
        })

        // Mengatur listener untuk ikon layanan
        icon1.setOnClickListener {
//            val latitude = -5.128871291996281
//            val longitude = 119.40862857598016

            val intent = Intent(this@MainActivity, PolisiActivity::class.java)
//            intent.putExtra("latitude", latitude)
//            intent.putExtra("longitude", longitude)
            startActivity(intent)
        }

        icon2.setOnClickListener {
            // Aksi yang diambil saat ikon 2 diklik
        }

        icon3.setOnClickListener {
            // Aksi yang diambil saat ikon 3 diklik
            val intent = Intent(this@MainActivity, RSActivity::class.java)
//            intent.putExtra("latitude", latitude)
//            intent.putExtra("longitude", longitude)
            startActivity(intent)
        }

        icon4.setOnClickListener {
            // Aksi yang diambil saat ikon 4 diklik
        }

        icon5.setOnClickListener {
            // Aksi yang diambil saat ikon 5 diklik
        }

        icon6.setOnClickListener {
            // Aksi yang diambil saat ikon 6 diklik
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Speech to text
        tvText = findViewById(R.id.tvText)
        btnSpeak = findViewById(R.id.btnSpeak)

        // Text to speech


        btnSpeak.setOnClickListener {
            val micGoogle = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            micGoogle.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            micGoogle.putExtra(RecognizerIntent.EXTRA_LANGUAGE, ID_BahasaIndonesia)

            try {
                startActivityForResult(micGoogle, RESULT_SPEECH)
                tvText.text = ""
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(applicationContext, "Maaf, Device Kamu Tidak Support Speech To Text", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Mengatur tampilan peta di lokasi pengguna saat ini
        enableLocation()
    }

    private fun enableLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        map.addMarker(MarkerOptions().position(currentLocation).title("My Location"))
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_SPEECH -> {
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    tvText.text = text?.get(0)

                    // Mendapatkan latitude dan longitude pengguna saat ini
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                val latitude = location.latitude
                                val longitude = location.longitude
//                                val locatiantext = "$latitude, $longitude"
//                                tvText.append(locatiantext)
                            }
                        }
                }
            }
        }


    }

}
