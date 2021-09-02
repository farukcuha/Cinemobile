package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.view.fragment.favorite.FavoritesFragmentDirections
import com.pandorina.cinemobile.view.holder.VerticalMovieItemHolder


class FavoriteMoviesAdapter: ListAdapter<FavoriteMovie, VerticalMovieItemHolder>(Comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieItemHolder {
        val binding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalMovieItemHolder(binding)
    }

    fun getFavoriteMovieItem(position: Int): FavoriteMovie = getItem(position)

    override fun onBindViewHolder(holder: VerticalMovieItemHolder, position: Int) {
        getItem(position).let {
            val movie = Movie(
                    it.poster_path,
                    it.overview,
                    it.release_date,
                    null,
                    it.movie_id,
                    null,
                    null,
                    it.title,
                    it.backdrop_path)
            holder.bind(movie)
            holder.itemView.setOnClickListener{ view ->
                val action = FavoritesFragmentDirections.actionNavFavoritesToNavMovieDetail(movie)
                view.findNavController().navigate(action)
            }
        }
    }

    object Comparator: DiffUtil.ItemCallback<FavoriteMovie>(){
        override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem.movie_id == newItem.movie_id
        }

        override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem == newItem
        }
    }
}