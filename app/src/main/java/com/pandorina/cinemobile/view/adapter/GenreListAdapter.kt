package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.databinding.ItemGenreBinding
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.view.fragment.common.GenreListFragmentDirections
import com.pandorina.cinemobile.view.holder.GenreHolder


class GenreListAdapter(var list: List<Genre>): RecyclerView.Adapter<GenreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val item = list[position]
        holder.bind(item)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val action = GenreListFragmentDirections.actionNavGenresToMoviesByGenresFragment(item)
            it.findNavController().navigate(action)
        })
    }

    override fun getItemCount(): Int = list.size
}