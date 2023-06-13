package com.capstone.demokoling
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val TLP: Long,


) : Parcelable

data class RequestBody(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("label")
    val label: String,
)

data class RequestBodyText(
    @SerializedName("Latitude")
    val latitude: Double,

    @SerializedName("Longitude")
    val longitude: Double,

    @SerializedName("bahaya anda")
    val bahaya_anda: String,
)