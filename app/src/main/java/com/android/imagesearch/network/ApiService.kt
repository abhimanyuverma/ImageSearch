package com.android.imagesearch.network

import com.android.imagesearch.model.ApiResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("?method=flickr.photos.search&api_key=a1f7988bc0c32b1353b32737657bf669&format=json&nojsoncallback=1&media=photos&per_page=500")
    suspend fun getImages(@Query("text") text: String): Response<ApiResponse>
}