package com.pandorina.cinemobile.util

import android.app.Activity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pandorina.cinemobile.R

fun ImageView.loadImage(url: String?, isRounded: Boolean? = false){
    Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(if(isRounded == true) 20 else 1))
            .error(R.drawable.image_place_holder)
            .into(this)


}

