package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Country
import com.pandorina.cinemobile.databinding.ItemProductionCountriesBinding
import com.pandorina.cinemobile.util.Util.loadImage

class CountryHolder(val binding: ItemProductionCountriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Country) {
        val flagImageUrl = "https://www.countryflags.io/${item.iso_3166_1}/shiny/64.png"
        binding.imageViewProductionCountryImage.loadImage(flagImageUrl)
        binding.textViewProductionCountryName.text = item.name
    }
}
