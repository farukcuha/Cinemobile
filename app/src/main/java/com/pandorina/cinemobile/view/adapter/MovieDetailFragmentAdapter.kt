package com.pandorina.cinemobile.view.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.view.fragment.movie.*

class MovieDetailFragmentAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        private val fragmentList: ArrayList<MovieDetailFragment.MovieDetailFragmentModel>,
        private val movieDetail: MovieDetail):
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment{
        val fragment = fragmentList[position].fragment
        val bundle = bundleOf("arg_movie_detail" to movieDetail)
        fragment.arguments = bundle
        return fragment
    }


}