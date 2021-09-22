package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.remote.model.Company
import com.pandorina.cinemobile.databinding.ItemProductionCompaniesBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.holder.CompanyHolder

class CompanyListAdapter : ListAdapter<Company, CompanyHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder {
        val binding = ItemProductionCompaniesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompanyHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}