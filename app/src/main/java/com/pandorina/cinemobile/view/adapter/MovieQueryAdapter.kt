package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.databinding.ItemSearchedQueryBinding
import com.pandorina.cinemobile.view.holder.SearchedQueryHolder

class MovieQueryAdapter(private val itemClickListener: OnItemClickListener): ListAdapter<MovieQuery, SearchedQueryHolder>(Comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedQueryHolder {
        val binding = ItemSearchedQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedQueryHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: SearchedQueryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object Comparator: DiffUtil.ItemCallback<MovieQuery>() {
        override fun areItemsTheSame(oldItem: MovieQuery, newItem: MovieQuery): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: MovieQuery, newItem: MovieQuery): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener{
        fun onQueryItem(query: String)
    }

}