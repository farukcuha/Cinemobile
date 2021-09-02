package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.view.fragment.movie.MovieSearchFragmentDirections
import com.pandorina.cinemobile.view.fragment.movie.MoviesByGenresFragmentDirections
import com.pandorina.cinemobile.view.fragment.movie.MoviesFragmentDirections
import com.pandorina.cinemobile.view.holder.VerticalMovieItemHolder

class SearchMoviesAdapter: PagingDataAdapter<Movie, VerticalMovieItemHolder>(Comparator) {
    override fun onBindViewHolder(holder: VerticalMovieItemHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)

            holder.itemView.setOnClickListener{
                val action = MovieSearchFragmentDirections.actionMovieSearchFragmentToNavMovieDetail(item)
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieItemHolder {
        val binding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalMovieItemHolder(binding)
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