package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.holder.VerticalItemHolder

class VerticalPagingAdapter : PagingDataAdapter<Movie, VerticalItemHolder>(Util.Comparator()) {
    override fun onBindViewHolder(holder: VerticalItemHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            it.navigate(R.id.detailFragment, Constant.ARG_MOVIE, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalItemHolder {
        val view =
            ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalItemHolder(view)
    }
}