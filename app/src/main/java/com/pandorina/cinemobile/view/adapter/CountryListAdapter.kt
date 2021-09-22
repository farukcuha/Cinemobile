package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pandorina.cinemobile.data.remote.model.Country
import com.pandorina.cinemobile.databinding.ItemProductionCountriesBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.holder.CountryHolder

class CountryListAdapter : ListAdapter<Country, CountryHolder>(Util.Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding = ItemProductionCountriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}