package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.databinding.ItemProductionCompaniesBinding
import com.pandorina.cinemobile.databinding.ItemProductionCountriesBinding
import com.pandorina.cinemobile.data.remote.model.Country
import com.pandorina.cinemobile.util.loadImage

class ProductionCountriesAdapter: RecyclerView.Adapter<ProductionCountriesAdapter.Holder>() {
    var list = listOf<Country>()
    class Holder(val binding: ItemProductionCountriesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country){
            val flagImageUrl = "https://www.countryflags.io/${item.iso_3166_1}/shiny/64.png"
            binding.imageViewProductionCountryImage.loadImage(flagImageUrl)
            binding.textViewProductionCountryName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductionCountriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}