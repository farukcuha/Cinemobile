package com.pandorina.cinemobile.view.fragment

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.FragmentDetailBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.view.adapter.DetailFragmentAdapter
import com.pandorina.cinemobile.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate, Constant.ARG_MOVIE) {
    private val viewModel: DetailViewModel by viewModels()
    var movie: Movie? = null

    override fun observeData() {
        movie = argument as Movie
        movie?.title?.let { setActionBarText(requireActivity(), it) }
        binding.imageViewMovieDetailBackdropImage.loadImage(movie?.backdrop_path_url)
        viewModel.currentMovieId.value = movie?.id
        viewModel.getMovieDetail()
    }

    override fun setUpViews() {
        setHasOptionsMenu(true)
        setUpViewPager()
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
            R.id.menu_add_favorite -> actionAddFavorite()
            R.id.menu_share -> actionShare()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun actionAddFavorite() {
        val favoriteMovie = FavoriteMovie(
            movie?.id,
            movie?.poster_path,
            movie?.title, movie?.overview,
            movie?.release_date,
            movie?.backdrop_path
        )
        viewModel.isFavoriteMovieExist.value.apply {
            if (this == true) {
                viewModel.deleteFavoriteMovie(favoriteMovie)
            } else {
                viewModel.insertFavoriteMovie(favoriteMovie)
            }
        }
    }

    private fun actionShare() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, movie?.title)
            putExtra(Intent.EXTRA_TEXT, "${Constant.WEB_URL}${movie?.id}")
        }, null)
        startActivity(share)
    }

    private fun setUpViewPager() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val movieDetail = networkResult.data
                    val tabLayout = binding.tabLayoutMovieDetail
                    val viewPager = binding.viewPagerMovieDetail

                    val fragmentList = arrayListOf<MovieDetailFragmentModel>()
                    fragmentList.add(
                        MovieDetailFragmentModel(
                            getString(R.string.overview),
                            OverviewFragment()
                        )
                    )
                    fragmentList.add(
                        MovieDetailFragmentModel(
                            getString(R.string.cast),
                            CastFragment()
                        )
                    )
                    movieDetail?.belongs_to_collection?.let {
                        fragmentList.add(
                            MovieDetailFragmentModel(
                                getString(R.string.collection),
                                CollectionFragment()
                            )
                        )
                    }
                    fragmentList.add(
                        MovieDetailFragmentModel(
                            getString(R.string.similar),
                            SimilarFragment()
                        )
                    )
                    fragmentList.add(
                        MovieDetailFragmentModel(
                            getString(R.string.videos),
                            VideosFragment()
                        )
                    )

                    viewPager.adapter = DetailFragmentAdapter(
                        childFragmentManager,
                        lifecycle,
                        fragmentList,
                        movieDetail
                    )

                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = fragmentList[position].title
                    }.attach()
                }
                is NetworkResult.Error -> {

                }
            }
        }

    }

    data class MovieDetailFragmentModel(val title: String, val fragment: Fragment)


}