package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.MovieDetailViewPagerAdapter
import com.pandorina.cinemobile.databinding.FragmentMovieDetailBinding
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.util.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment: Fragment(R.layout.fragment_movie_detail) {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieDetailBinding.bind(view)

        arguments?.let{
            val item = it.get("movie_arg") as Movie
            setUpViewPager(item)
            (activity as AppCompatActivity).supportActionBar?.title = item.title
            binding.imageViewMovieDetailBackdropImage.loadImage(item.backdrop_path_url)
        }
    }

    private fun setUpViewPager(movie: Movie) {
        val tabLayout = binding.tabLayoutMovieDetail
        val viewPager = binding.viewPagerMovieDetail

        val adapter = MovieDetailViewPagerAdapter(childFragmentManager, lifecycle, movie)
        viewPager.adapter = adapter

        val list = arrayOf(
            getString(R.string.overview), getString(R.string.cast),
            getString(R.string.collection), getString(R.string.similar),
            getString(R.string.videos), getString(R.string.image)
        )

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = list[position]
        }.attach()
    }
}