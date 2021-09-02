package com.pandorina.cinemobile.view.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentFavoritesBinding
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.view.adapter.FavoritesFragmentAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFavoritesBinding.bind(view)

        setActionBarText(requireActivity(), getString(R.string.favorite))

        val tabLayout = binding.tabLayoutFavorites
        val viewPager = binding.viewPagerFavorites

        viewPager.adapter = FavoritesFragmentAdapter(childFragmentManager, lifecycle)

        val favoriteFragmentsList = arrayOf(getString(R.string.movies), getString(R.string.series))

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = favoriteFragmentsList[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}