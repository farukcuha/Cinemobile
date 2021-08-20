package com.pandorina.cinemobile.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.loadImage
import com.pandorina.cinemobile.view.fragment.movie.MoviesFragmentDirections

class DiscoverMovieViewPagerAdapter(private val list: ArrayList<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var isLoaded: Boolean? = false

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_view_view_pager_item_image)
        val title: TextView = itemView.findViewById(R.id.text_view_view_pager_item_text)
    }

    class ShimmerHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isLoaded == true) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
            ItemHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.shimmer_item_view_pager, parent, false)
            ShimmerHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            val item = list[position]
            holder.image.loadImage(item.backdrop_path_url)
            holder.title.text = item.title

            holder.itemView.setOnClickListener(View.OnClickListener {
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(item)
                holder.itemView.findNavController().navigate(action)
            })
        }




    }

    override fun getItemCount(): Int = if (isLoaded == true) list.size else 1

    override fun getItemViewType(position: Int): Int = if (isLoaded == true) Constant.ITEM_TYPE_MOVIE else Constant.ITEM_TYPE_SHIMMER

    fun updateList(updatedList: ArrayList<Movie>) {
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }
}