package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.holder.VerticalItemHolder

class VerticalListAdapter : ListAdapter<Movie, VerticalItemHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalItemHolder {
        val binding =
            ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalItemHolder((binding))
    }

    override fun onBindViewHolder(holder: VerticalItemHolder, position: Int) {
        getItem(position).let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                it.navigate(R.id.detailFragment, Constant.ARG_MOVIE, movie)
            }
        }
    }
}