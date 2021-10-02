package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemTrendMovieBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.holder.TrendingHolder

class TrendingAdapter: ListAdapter<Movie, TrendingHolder>(Util.Comparator()) {
    var currentScrollPosition: Int = 10
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingHolder {
        val binding = ItemTrendMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingHolder((binding))
    }

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            it.navigate(R.id.detailFragment, Constant.ARG_MOVIE, item)
            currentScrollPosition = position
        }
    }
}