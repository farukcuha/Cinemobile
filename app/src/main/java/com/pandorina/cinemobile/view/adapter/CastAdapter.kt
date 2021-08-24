package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.pandorina.cinemobile.databinding.FragmentCastBinding
import com.pandorina.cinemobile.databinding.ItemCastBinding
import com.pandorina.cinemobile.data.model.Credit
import com.pandorina.cinemobile.util.loadImage

class CastAdapter(var list: ArrayList<Credit>): RecyclerView.Adapter<CastAdapter.Holder>() {
    class Holder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Credit){
            binding.apply {
                imageViewCastImage.loadImage(item.profile_path_url)
                textViewCastRealName.text = item.name
                textViewCastCharacterName.text = item.character
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun update(updatedList: List<Credit>){
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }
}