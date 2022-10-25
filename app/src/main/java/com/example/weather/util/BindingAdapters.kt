package com.example.weather.util

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.weather.R

@BindingAdapter("bitmap", requireAll = false)
fun setImage(view:ImageView, bitmap:Bitmap?){

    view.load(bitmap){
        error(R.drawable.ic_baseline_broken_image_24)
    }
}