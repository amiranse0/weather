package com.example.weather.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.example.weather.R

@BindingAdapter("imageUrl", requireAll = false)
fun setImage(view:ImageView, imageUrl:String?){

    view.load(imageUrl){
        error(R.drawable.ic_baseline_broken_image_24)
    }
}