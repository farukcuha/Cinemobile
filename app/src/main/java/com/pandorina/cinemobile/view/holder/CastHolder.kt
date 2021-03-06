package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Credit
import com.pandorina.cinemobile.databinding.ItemCastBinding
import com.pandorina.cinemobile.util.Util.loadImage

class CastHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Credit){
        binding.apply {
            imageViewCastImage.loadImage(item.profilePathUrl)
            textViewCastRealName.text = item.name
            textViewCastCharacterName.text = item.character
        }
    }
}
