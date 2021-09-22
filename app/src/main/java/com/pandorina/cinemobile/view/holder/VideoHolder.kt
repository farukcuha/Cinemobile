package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.VideoResponse
import com.pandorina.cinemobile.databinding.ItemVideoBinding
import com.pandorina.cinemobile.util.Util.loadImage

class VideoHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: VideoResponse.Video) {
        binding.apply {
            imageViewVideoThumbnail.loadImage(item.thumbnailUrl)
            textViewVideoTitle.text = item.name
            textViewVideoType.text = item.type
        }
    }
}