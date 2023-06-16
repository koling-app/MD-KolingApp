package com.capstone.demokoling
import android.os.Parcel
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

    @SerializedName("Longitude")
    val longitude: Double,

    @SerializedName("Latitude")
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

data class RegisterRequest(
    @SerializedName("nik") val nik: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pass") val password: String
)

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserData
)

@Parcelize
data class UserData(
    @SerializedName("nik") val nik: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pass") val password: String
) : Parcelable




data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("loginResult")
    val loginResult: LoginResult,

    @SerializedName("token")
    val token: String
)

data class LoginResult(
    @SerializedName("userid")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String
)

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("pass")
    val password: String
)

data class StoryResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Story>
)
@Parcelize
data class Story(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("photoUrl")
    val photoUrl: String,

    @SerializedName("createAt")
    val createAt: String?,

    @SerializedName("lan")
    val latitude: String,

    @SerializedName("lon")
    val longitude: String
) : Parcelable

data class ServicesResponse(
    val data: List<ServiceData>
)
@Parcelize
data class ServiceData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar")
    val avatar: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("lat")
    val lat: String,

    @SerializedName("lon")
    val lon: String
) :Parcelable

data class UploadStoryRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photoUrl") val photoUrl: String,
    @SerializedName("lan") val latitude: String,
    @SerializedName("lon") val longitude: String
)

data class UploadStoryResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UploadStoryData
)

data class UploadStoryData(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photoUrl") val photoUrl: String,
    @SerializedName("lan") val latitude: String,
    @SerializedName("lon") val longitude: String
)

data class UserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<User>
)

@Parcelize
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("nik") val nik: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pass") val pass: String
) : Parcelable



data class ApiResponse(
    val message: String,
    val data: List<Posko>
)

@Parcelize
data class Posko(
    val id: String,
    val name: String,
    val avatar: String,
    val phone: String,
    val lat: String,
    val lon: String
) : Parcelable
    // Implement the Parcelable interface methods
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        // Write your properties to the Parcel
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Posko> {
//        override fun createFromParcel(parcel: Parcel): Posko {
//            // Read the Parcel and create a new Posko object
//        }
//
//        override fun newArray(size: Int): Array<Posko?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

