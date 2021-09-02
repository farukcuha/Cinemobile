package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.view.fragment.movie.MovieDetailFragmentDirections
import com.pandorina.cinemobile.view.holder.VerticalMovieItemHolder

class MovieCollectionAdapter: ListAdapter<Movie, VerticalMovieItemHolder>(Comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieItemHolder {
        val binding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalMovieItemHolder((binding))
    }

    override fun onBindViewHolder(holder: VerticalMovieItemHolder, position: Int) {
        getItem(position).let { movie ->
            holder.bind(movie)

            holder.itemView.setOnClickListener{
                val action = MovieDetailFragmentDirections.actionNavMovieDetailSelf(movie)
                it.findNavController().navigate(action)
            }
        }
    }

    object Comparator: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}