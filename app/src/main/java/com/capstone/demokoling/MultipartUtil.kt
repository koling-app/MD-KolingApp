package com.capstone.demokoling

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultipartUtil {
    fun prepareFilePart(file: File, partName: String): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaType())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}
