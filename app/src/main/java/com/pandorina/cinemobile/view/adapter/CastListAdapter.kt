package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.remote.model.Credit
import com.pandorina.cinemobile.databinding.ItemCastBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.holder.CastHolder

class CastListAdapter : ListAdapter<Credit, CastHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastHolder(binding)
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}