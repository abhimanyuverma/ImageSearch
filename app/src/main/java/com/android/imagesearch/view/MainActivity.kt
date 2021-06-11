package com.android.imagesearch.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.R
import com.android.imagesearch.databinding.ActivityMainBinding
import com.android.imagesearch.model.Photo
import com.android.imagesearch.network.Status
import com.android.imagesearch.viewmodel.ImageViewModel
import com.android.imagesearch.viewmodel.ImageViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: ImageViewModelFactory by instance()

    private lateinit var viewModel: ImageViewModel

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var gridLayoutManager: GridLayoutManager

    private var photos: ArrayList<Photo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel =
            ViewModelProvider(this, factory).get(ImageViewModel::class.java)

        gridLayoutManager = GridLayoutManager(this, 2)
        binding.imageRecyclerView.layoutManager = gridLayoutManager

        viewModel.getImage.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    photos.addAll(it.data!!.photos.photo)
                    if (photos.size > 0) {
                        binding.tvMessage.visibility = View.GONE
                    } else {
                        binding.tvMessage.visibility = View.VISIBLE
                    }
                    imageAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                }
                else -> {
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                photos.clear()
                if (!TextUtils.isEmpty(query))
                    viewModel.getImage(query)
                else
                    binding.tvMessage.visibility = View.VISIBLE
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                photos.clear()
                if (!TextUtils.isEmpty(newText))
                    viewModel.getImage(newText)
                else
                    binding.tvMessage.visibility = View.VISIBLE
                return false
            }
        })

        imageAdapter = ImageAdapter(photos)
        binding.imageRecyclerView.adapter = imageAdapter
        binding.imageRecyclerView.setHasFixedSize(true)

        binding.imageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}