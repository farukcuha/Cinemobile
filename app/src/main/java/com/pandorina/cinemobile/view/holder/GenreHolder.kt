package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.databinding.ItemGenreBinding

class GenreHolder(val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Genre){
        binding.root.text = item.name
    }
}