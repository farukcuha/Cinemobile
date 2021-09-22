package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : ViewBinding, Y : RecyclerView.ViewHolder, A>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T,
    private val viewHolder: (ViewBinding) -> Y
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = arrayListOf<A>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: T = inflateMethod.invoke(LayoutInflater.from(parent.context), parent, false)
        return viewHolder.invoke(binding)
    }

    override fun getItemCount() = list.size

    fun updateList(updatedList: ArrayList<A>) {
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }
}