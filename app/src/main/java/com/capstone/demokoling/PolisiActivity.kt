package com.capstone.demokoling

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.demokoling.ResponseDataAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PolisiActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: ResponseDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polisi)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        adapter = ResponseDataAdapter(emptyList())
        recyclerView.adapter = adapter

        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val requestBody = RequestBody(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        label = "Polisi"
                    )

                    val call = ApiClient.apiService.postData(requestBody)
                    call.enqueue(object : Callback<List<ResponseData>> {
                        override fun onResponse(
                            call: Call<List<ResponseData>>,
                            response: Response<List<ResponseData>>
                        ) {
                            if (response.isSuccessful) {
                                val responseDataList = response.body()
                                responseDataList?.let {
                                    adapter.setData(it)
                                    adapter.notifyDataSetChanged()
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
