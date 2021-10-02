package com.pandorina.cinemobile.view.holder

import android.view.animation.Transformation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemTrendMovieBinding
import com.pandorina.cinemobile.util.Util.loadImage
import jp.wasabeef.glide.transformations.BitmapTransformation
import jp.wasabeef.glide.transformations.BlurTransformation

class TrendingHolder(val binding: ItemTrendMovieBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie?){
        binding.ivImage.loadImage(movie?.poster_path_url)
    }
}
