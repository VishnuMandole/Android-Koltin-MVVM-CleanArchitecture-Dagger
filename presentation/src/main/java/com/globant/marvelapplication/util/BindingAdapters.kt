package com.marvel.marvelapplication.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.marvel.marvelapplication.R

object BindingAdapters {
    @BindingAdapter("loadThumbnail")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .centerInside()
            .into(view)
    }
}