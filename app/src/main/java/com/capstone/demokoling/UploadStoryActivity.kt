package com.capstone.demokoling

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.capstone.demokoling.SharedPreferencesUtil.getEmailFromSharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadStoryActivity : AppCompatActivity() {
    // Constants for permission request and camera capture
    private companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val CAMERA_CAPTURE_REQUEST_CODE = 200
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var photoUri: Uri
    private lateinit var capturedPhotoPath: String
    private lateinit var imageView: ImageView
    private lateinit var captureButton: Button
    private lateinit var uploadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_story)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Tampilkan Main Activity
                    startActivity(Intent(this@UploadStoryActivity, MainActivity::class.java))
                    true
                }
                R.id.navigation_services -> {
                    // Tampilkan Services Activity
                    startActivity(Intent(this@UploadStoryActivity, ServicesActivity::class.java))
                    true
                }
                R.id.navigation_upload_story -> {
                    // Tampilkan Upload Story Activity
                    true
                }
                R.id.navigation_view_story -> {
                    // Tampilkan View Story Activity
                    startActivity(Intent(this@UploadStoryActivity, StoryActivity::class.java))
                    true
                }
                R.id.navigation_account_settings -> {
                    // Tampilkan Account Settings Activity
                    startActivity(Intent(this@UploadStoryActivity, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set Setting Activity sebagai halaman yang aktif secara default
        bottomNavigationView.selectedItemId = R.id.navigation_upload_story

        // Initialize views
        imageView = findViewById(R.id.image_view)
        captureButton = findViewById(R.id.button_capture)
        uploadButton = findViewById(R.id.button_upload)

        // Set click listener for capture button
        captureButton.setOnClickListener {
            checkCameraPermissionAndCapture()
        }

        // Set click listener for upload button
        uploadButton.setOnClickListener {
            uploadStory()
        }
    }

    private fun checkCameraPermissionAndCapture() {
        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            capturePhoto()
        } else {
            // Request camera permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun capturePhoto() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (captureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            val photoFile = createImageFile()
            photoUri = FileProvider.getUriForFile(this, "com.capstone.demokoling.fileprovider", photoFile)

            // Pass the photo file to the capture intent
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            // Start the camera capture activity
            startActivityForResult(captureIntent, CAMERA_CAPTURE_REQUEST_CODE)
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = getExternalFilesDir(null)
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)

        capturedPhotoPath = imageFile.absolutePath
        return imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Display the captured photo
            displayCapturedPhoto()
        }
    }

    private fun displayCapturedPhoto() {
        // Load the captured photo into an ImageView
        val bitmap = decodeSampledBitmapFromFile(capturedPhotoPath, imageView.width, imageView.height)
        imageView.setImageBitmap(bitmap)
    }

    private fun decodeSampledBitmapFromFile(filePath: String, reqWidth: Int, reqHeight: Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, this)
            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false
        }

        // Ensure that reqWidth and reqHeight are valid
        val width = options.outWidth
        val height = options.outHeight
        val validReqWidth = if (reqWidth > 0) reqWidth else width
        val validReqHeight = if (reqHeight > 0) reqHeight else height

        return BitmapFactory.decodeFile(filePath, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (reqWidth > 0 && reqHeight > 0 && (height > reqHeight || width > reqWidth)) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun uploadStory() {
        // Get other input values (description)
        val descriptionEditText = findViewById<EditText>(R.id.edit_text_description)
        val description = descriptionEditText.text.toString()

        // Validate input (e.g., check if all fields have been filled)
        if (description.isEmpty() || capturedPhotoPath.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the logged-in user's email
        val loggedInUserEmail = getEmailFromSharedPreferences(this) // Replace this with the actual method to retrieve the logged-in user's email

        // Request location updates
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()

                // Call the API to retrieve user data
                val userService = ApiClient.apiService
                userService.getUsers().enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            val userResponse = response.body()
                            if (userResponse != null) {
                                val users = userResponse.data

                                // Find the user with the matching email
                                val loggedInUser = users.find { it.email == loggedInUserEmail }

                                if (loggedInUser != null) {
                                    val loggedInUserName = loggedInUser.name

                                    // Save the captured photo as JPEG in the desired directory
                                    val photoFile = File(capturedPhotoPath)
                                    val bitmap = BitmapFactory.decodeFile(capturedPhotoPath)
                                    val jpegFile = saveBitmapAsJPEG(bitmap, photoFile)

                                    // Check if the JPEG file was successfully saved
                                    if (jpegFile != null) {
                                        // Create the MultipartBody.Part for photoUrl
                                        val requestBody = MultipartBody.Part.createFormData(
                                            "photoUrl",
                                            jpegFile.name,
                                            jpegFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                                        )

                                        // Proceed with uploading the story using the requestBody, loggedInUserName, latitude, and longitude
                                        val apiService = ApiClient.apiService
                                        apiService.uploadStory(requestBody, description).enqueue(object : Callback<UploadStoryResponse> {
                                            override fun onResponse(call: Call<UploadStoryResponse>, response: Response<UploadStoryResponse>) {
                                                if (response.isSuccessful) {
                                                    // Story uploaded successfully
                                                    val uploadStoryResponse = response.body()
                                                    // Handle the response as needed
                                                } else {
                                                    // Error uploading the story
                                                    Toast.makeText(this@UploadStoryActivity, "Failed to upload story", Toast.LENGTH_SHORT).show()
                                                }
                                                resetInputFields()
                                            }

                                            override fun onFailure(call: Call<UploadStoryResponse>, t: Throwable) {
                                                // Error uploading the story
                                                Toast.makeText(this@UploadStoryActivity, "Failed to upload story", Toast.LENGTH_SHORT).show()
                                                resetInputFields()
                                            }
                                        })
                                    } else {
                                        // Failed to save the photo as JPEG
                                        Toast.makeText(this@UploadStoryActivity, "Failed to save photo", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(this@UploadStoryActivity, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Handle network error or failed API call
                        Toast.makeText(this@UploadStoryActivity, "Network request failed", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                // Failed to get location
                Toast.makeText(this, "Failed to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun resetInputFields() {
        val descriptionEditText = findViewById<EditText>(R.id.edit_text_description)
        descriptionEditText.text.clear()
        imageView.setImageBitmap(null)
        capturedPhotoPath = ""
    }

    private fun saveBitmapAsJPEG(bitmap: Bitmap, photoFile: File): File? {
        return try {
            val fos = FileOutputStream(photoFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            photoFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capturePhoto()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
