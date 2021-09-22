package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.ItemHorizontalListBinding
import com.pandorina.cinemobile.util.Util.loadImage

class HorizontalItemHolder(val binding: ItemHorizontalListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.apply {
            imageViewImage.loadImage(item.poster_path_url)
            textViewName.text = item.title
            textViewReleaseYear.text = item.release_date
        }
    }
}
