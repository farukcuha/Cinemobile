package com.pandorina.cinemobile.view.fragment.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.FragmentMovieDetailBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.loadImage
import com.pandorina.cinemobile.view.adapter.MovieDetailViewPagerAdapter
import com.pandorina.cinemobile.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment: Fragment(R.layout.fragment_movie_detail) {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    private var _movie: Movie? = null
    val movie get() = _movie!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieDetailBinding.bind(view)

        arguments?.let {
            _movie = it.get("movie_arg") as Movie
        }

        setUpViewPager(movie)
        (activity as AppCompatActivity).supportActionBar?.title = movie.title
        binding.imageViewMovieDetailBackdropImage.loadImage(movie.backdrop_path_url)
        viewModel.currentMovieId.value = movie.id
        viewModel.checkFavoriteMovieIsExist()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_detail_menu, menu)
        val menuAddToFavorite = menu.findItem(R.id.menu_add_favorite)

        viewModel.isFavoriteMovieExist.observe(viewLifecycleOwner) {
            menuAddToFavorite.icon =
                    if (it) ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_filled)
                    else ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_empty)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_favorite -> {
                val favoriteMovie = FavoriteMovie(movie.id)
                viewModel.isFavoriteMovieExist.value.apply {
                    if (this == true) {
                        viewModel.deleteFavoriteMovie(favoriteMovie)
                    } else {
                        viewModel.insertFavoriteMovie(favoriteMovie)
                    }
                }
            }
            R.id.menu_share -> {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, movie.title)
                    putExtra(Intent.EXTRA_TEXT, "${Constant.WEB_URL}${movie.id}")
                }, null)
                startActivity(share)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
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