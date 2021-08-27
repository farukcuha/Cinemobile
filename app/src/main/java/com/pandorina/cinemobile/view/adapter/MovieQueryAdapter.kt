package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.model.MovieQuery
import com.pandorina.cinemobile.databinding.ItemSearchQueryItemBinding

class MovieQueryAdapter(private val itemClickListener: OnItemClickListener): ListAdapter<MovieQuery, MovieQueryAdapter.Holder>(Comparator) {
    class Holder(
            val binding: ItemSearchQueryItemBinding,
            private val mItemClickListener: OnItemClickListener
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

    interface OnItemClickListener{
        fun onQueryItem(query: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieQueryAdapter.Holder {
        val binding = ItemSearchQueryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MovieQueryAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }

    object Comparator: DiffUtil.ItemCallback<MovieQuery>() {
        override fun areItemsTheSame(oldItem: MovieQuery, newItem: MovieQuery): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieQuery, newItem: MovieQuery): Boolean {
            return oldItem == newItem
        }

    }

}