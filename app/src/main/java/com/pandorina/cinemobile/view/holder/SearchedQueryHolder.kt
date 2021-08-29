package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.databinding.ItemSearchedQueryBinding
import com.pandorina.cinemobile.view.adapter.MovieQueryAdapter

class SearchedQueryHolder(
        val binding: ItemSearchedQueryBinding,
        private val mItemClickListener: MovieQueryAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: MovieQuery){
        binding.textViewQuery.apply {
            text = item.query
            setOnClickListener{
                mItemClickListener.onQueryItem(item.query)
            }
        }
    }
}