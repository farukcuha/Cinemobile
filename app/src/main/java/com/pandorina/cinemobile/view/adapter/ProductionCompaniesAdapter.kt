package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.databinding.ItemProductionCompaniesBinding
import com.pandorina.cinemobile.data.remote.model.Company
import com.pandorina.cinemobile.util.Util.loadImage

class ProductionCompaniesAdapter: RecyclerView.Adapter<ProductionCompaniesAdapter.Holder>(){
    var list = listOf<Company>()
    class Holder(val binding: ItemProductionCompaniesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Company){
            binding.imageViewProductionCompanyImage.loadImage(item.logo_path_url)
            binding.textViewProductionCompanyName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductionCompaniesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}