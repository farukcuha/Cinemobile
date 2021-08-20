package com.pandorina.cinemobile.view.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.view.fragment.movie.*

class MovieDetailViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        private val movie: Movie):
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment{
        val bundle = bundleOf("movie" to movie)
        val fragment: Fragment = when(position){
            0 -> MovieOverviewFragment()
            1 -> CastFragment()
            2 -> MovieCollectionFragment()
            3 -> MovieSimilarFragment()
            4 -> MovieVideosFragment()
            5 -> MovieImagesFragment()
            else -> MovieOverviewFragment()
        }
        fragment.arguments = bundle
        return fragment
    }


}