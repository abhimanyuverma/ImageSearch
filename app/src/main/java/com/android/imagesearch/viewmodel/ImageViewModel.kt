package com.android.imagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.imagesearch.model.ApiResponse
import com.android.imagesearch.network.ImageRepo
import com.android.imagesearch.network.Resource
import com.android.imagesearch.network.Status
import kotlinx.coroutines.launch

class ImageViewModel(
    private val repo: ImageRepo,
    app: Application
) : AndroidViewModel(app) {

    private val _getImageLiveData: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val getImage: LiveData<Resource<ApiResponse>> = _getImageLiveData

    fun getImage(query: String) {
        viewModelScope.launch {
            val apiResult = repo.getImages(query)
            _getImageLiveData.postValue(apiResult)
            if (apiResult.status == Status.SUCCESS) {
            } else {
            }
        }
    }
}


