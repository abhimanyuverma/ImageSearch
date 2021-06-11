package com.android.imagesearch.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response

class ImageRepo(private val apiService: ApiService) {

    suspend fun getImages(text: String) =
        safeApiCall { apiService.getImages(text) }
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> = try {
    val apiResult = call.invoke()
    if (apiResult.isSuccessful)
        Resource.success(apiResult.body()!!)
    else
        Resource.error(
            when (apiResult.code()) {
                401 -> "Authentication failed"
                else -> "Something went wrong"
            }
        )
} catch (e: Exception) {
    Resource.error("${e.message}")
}

data class ImageRequest(
    @SerializedName("api_key") val api_key: String,
    @SerializedName("format") val format: String,
    @SerializedName("media") val media: String,
    @SerializedName("nojsoncallback") val nojsoncallback: String,
    @SerializedName("text") val text: String
)
