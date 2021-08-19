package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.pandorina.cinemobile.databinding.ItemProductionCompaniesBinding
import com.pandorina.cinemobile.model.Company
import com.pandorina.cinemobile.util.loadImage

class ProductionCompaniesAdapter(val list: ArrayList<Company>): RecyclerView.Adapter<ProductionCompaniesAdapter.Holder>(){
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

    fun updateList(updatedList: List<Company>){
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }
}