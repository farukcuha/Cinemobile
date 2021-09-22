package com.pandorina.cinemobile.view.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.remote.model.VideoResponse
import com.pandorina.cinemobile.databinding.ItemVideoBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.holder.VideoHolder

class VideoListAdapter : ListAdapter<VideoResponse.Video, VideoHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + item.key))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.videoUrl))
            try {
                holder.itemView.context.startActivity(appIntent)
            } catch (e: ActivityNotFoundException) {
                holder.itemView.context.startActivity(webIntent)
            }
        }
    }
}