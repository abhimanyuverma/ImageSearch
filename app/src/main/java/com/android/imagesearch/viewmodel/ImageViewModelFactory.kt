package com.android.imagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.imagesearch.network.ImageRepo

class ImageViewModelFactory(
    private val imageRepo: ImageRepo,
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepo, app) as T
    }
}