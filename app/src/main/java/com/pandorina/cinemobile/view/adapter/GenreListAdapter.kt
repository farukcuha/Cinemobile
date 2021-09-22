package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.databinding.ItemGenreBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.holder.GenreHolder


class GenreListAdapter : ListAdapter<Genre, GenreHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            it.navigate(R.id.byGenresFragment, Constant.ARG_GENRE, item)
        }
    }
}