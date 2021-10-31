package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemTrendMovieBinding
import com.pandorina.cinemobile.util.Util.loadImage

class TrendingHolder(val binding: ItemTrendMovieBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie?){
        binding.ivImage.loadImage(movie?.posterPathUrl)
    }
}
