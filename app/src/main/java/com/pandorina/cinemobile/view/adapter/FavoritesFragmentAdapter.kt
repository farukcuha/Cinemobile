package com.pandorina.cinemobile.view.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pandorina.cinemobile.view.fragment.favorite.FavoriteMoviesFragment

class FavoritesFragmentAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
            when(position){
                0 -> FavoriteMoviesFragment()
                else -> FavoriteMoviesFragment()
            }
}