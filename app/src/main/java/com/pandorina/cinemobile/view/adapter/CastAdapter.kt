package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.pandorina.cinemobile.databinding.FragmentCastBinding
import com.pandorina.cinemobile.databinding.ItemCastBinding
import com.pandorina.cinemobile.data.remote.model.Credit
import com.pandorina.cinemobile.util.loadImage
import com.pandorina.cinemobile.view.holder.CastHolder

class CastAdapter(var list: ArrayList<Credit>): RecyclerView.Adapter<CastHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastHolder(binding)
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
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