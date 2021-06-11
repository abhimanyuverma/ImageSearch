package com.android.imagesearch

import android.app.Application
import com.android.imagesearch.network.ApiBuilder
import com.android.imagesearch.network.ImageRepo
import com.android.imagesearch.viewmodel.ImageViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class Application : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from provider { ImageRepo(ApiBuilder.apiService) }
        bind() from provider { ImageViewModelFactory(instance(), this@Application) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}