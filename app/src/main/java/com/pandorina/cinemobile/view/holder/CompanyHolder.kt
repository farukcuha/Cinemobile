package com.pandorina.cinemobile.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.data.remote.model.Company
import com.pandorina.cinemobile.databinding.ItemProductionCompaniesBinding
import com.pandorina.cinemobile.util.Util.loadImage

class CompanyHolder(val binding: ItemProductionCompaniesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Company) {
        binding.imageViewProductionCompanyImage.loadImage(item.logo_path_url)
        binding.textViewProductionCompanyName.text = item.name
    }
}
