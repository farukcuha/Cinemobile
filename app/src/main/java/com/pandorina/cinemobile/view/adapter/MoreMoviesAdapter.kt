package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.view.fragment.movie.*
import com.pandorina.cinemobile.view.holder.VerticalMovieItemHolder

class MoreMoviesAdapter : PagingDataAdapter<Movie, VerticalMovieItemHolder>(Comparator) {

    override fun onBindViewHolder(holder: VerticalMovieItemHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener(View.OnClickListener {
                val action = MoreMoviesFragmentDirections.actionNavMoreMoviesToNavMovieDetail(item)
                holder.itemView.findNavController().navigate(action)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieItemHolder {
        val view = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalMovieItemHolder(view)
    }

    object Comparator: DiffUtil.ItemCallback<Movie>(){
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }




}