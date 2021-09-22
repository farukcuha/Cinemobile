package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemViewPagerBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.holder.DiscoverHolder

class DiscoverListAdapter :
    ListAdapter<Movie, DiscoverHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscoverHolder(view)
    }

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
            it.navigate(R.id.detailFragment, Constant.ARG_MOVIE, item)
        }
    }
}