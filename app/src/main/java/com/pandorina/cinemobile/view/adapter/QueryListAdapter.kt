package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.databinding.ItemSearchedQueryBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.holder.QueryItemHolder

class QueryListAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<MovieQuery, QueryItemHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryItemHolder {
        val binding =
            ItemSearchedQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QueryItemHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(itemHolder: QueryItemHolder, position: Int) {
        itemHolder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onQueryItem(query: String)
    }

}