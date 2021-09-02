package com.pandorina.cinemobile.view.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentMovieDetailBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.view.adapter.MovieDetailFragmentAdapter
import com.pandorina.cinemobile.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.pandorina.cinemobile.util.Util.setActionBarText
import kotlinx.coroutines.launch


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

        movie.title?.let { setActionBarText(requireActivity(), it) }
        binding.imageViewMovieDetailBackdropImage.loadImage(movie.backdrop_path_url)
        viewModel.currentMovieId.value = movie.id
        setUpViewPager()
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
                val favoriteMovie = FavoriteMovie(movie.id, movie.poster_path, movie.title, movie.overview, movie.release_date, movie.backdrop_path)
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

    private fun setUpViewPager() {
        lifecycleScope.launch{
            viewModel.getMovieDetail.observe(viewLifecycleOwner){
               it?.let {
                   val tabLayout = binding.tabLayoutMovieDetail
                   val viewPager = binding.viewPagerMovieDetail

                   val fragmentList = arrayListOf<MovieDetailFragmentModel>()
                   fragmentList.add(MovieDetailFragmentModel(getString(R.string.overview), MovieOverviewFragment()))
                   fragmentList.add(MovieDetailFragmentModel(getString(R.string.cast), CastFragment()))
                   it.belongs_to_collection?.let {
                       fragmentList.add(MovieDetailFragmentModel(getString(R.string.collection), MovieCollectionFragment()))
                   }
                   fragmentList.add(MovieDetailFragmentModel(getString(R.string.similar), MovieSimilarFragment()))
                   fragmentList.add(MovieDetailFragmentModel(getString(R.string.videos), MovieVideosFragment()))
                   fragmentList.add(MovieDetailFragmentModel(getString(R.string.image), MovieImagesFragment()))

                   viewPager.adapter = MovieDetailFragmentAdapter(childFragmentManager, lifecycle, fragmentList, it)

                   TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                       tab.text = fragmentList[position].title
                   }.attach()
               }
            }
        }

    }

    data class MovieDetailFragmentModel(val title: String, val fragment: Fragment)
}