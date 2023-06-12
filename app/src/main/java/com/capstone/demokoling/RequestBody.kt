package com.capstone.demokoling
import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("KETERANGAN")
    val KETERANGAN: String?,

    @SerializedName("Label")
    val label: String?,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("TLP")
    val TLP: Long
)

data class RequestBody(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("label")
    val label: String
)
