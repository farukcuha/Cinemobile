package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.databinding.ItemGenreBinding
import com.pandorina.cinemobile.data.model.Genre
import com.pandorina.cinemobile.view.fragment.common.GenreListFragmentDirections


class GenreListAdapter(var list: List<Genre>): RecyclerView.Adapter<GenreListAdapter.Holder>() {
    class Holder(val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre){
            binding.root.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val action = GenreListFragmentDirections.actionNavGenresToMoviesByGenresFragment(item)
            it.findNavController().navigate(action)
        })
    }

    override fun getItemCount(): Int = list.size



}