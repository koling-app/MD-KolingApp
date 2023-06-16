package com.capstone.demokoling

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val requestBodyText = RequestBodyText(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        bahaya_anda = intent.getStringExtra("text") ?: "Default Label"
                    )

                    val call = ApiClient.apiService.postDataTeks(requestBodyText)
                    call.enqueue(object : Callback<List<ResponseData>> {
                        override fun onResponse(
                            call: Call<List<ResponseData>>,
                            response: Response<List<ResponseData>>
                        ) {
                            if (response.isSuccessful) {
                                val responseDataList = response.body()
                                responseDataList?.let {
                                    // Kirim data ke DetailResultActivity
                                    val intent = Intent(this@ResultActivity, DetailResultActivity::class.java)
                                    intent.putExtra("data", it[0]) // Anda dapat memilih elemen data yang ingin Anda kirim
                                    startActivity(intent)
                                    finish() // Selesai dengan ResultActivity
                                }
                            } else {
                                Log.e("API Error", "Response code: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                            Log.e("API Error", t.message ?: "Unknown error")
                        }
                    })
                }
            }
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun checkLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
