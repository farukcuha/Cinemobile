package com.pandorina.cinemobile.view.holder

import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.databinding.ItemVerticalListBinding
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.util.Util.loadImage

class VerticalItemHolder(private val binding: ItemVerticalListBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Movie){
        binding.image.loadImage(item.poster_path_url)
        binding.textViewName.text = item.title
        binding.textViewReleaseYear.text = item.release_date

        val expandableOverview = binding.textViewOverview
        val expandButton = binding.imageButtonExpand

        expandableOverview.text = item.overview
        expandableOverview.collapseInterpolator = OvershootInterpolator()
        expandableOverview.expandInterpolator = OvershootInterpolator()

        expandButton.setOnClickListener{
            if (expandableOverview.isExpanded){
                expandableOverview.collapse()
                expandButton.rotation = 0f
            } else{
                expandableOverview.expand()
                expandButton.rotation = 180f
            }
        }
    }
}