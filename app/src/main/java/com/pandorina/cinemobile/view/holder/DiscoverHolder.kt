package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemViewPagerBinding
import com.pandorina.cinemobile.util.Util.loadImage

class DiscoverHolder(val binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.apply {
            imageViewViewPagerItemImage.loadImage(item.backdropPathUrl)
            textViewViewPagerItemText.text = item.title
        }
    }
}
