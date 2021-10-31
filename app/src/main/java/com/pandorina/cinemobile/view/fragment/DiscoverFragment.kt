package com.pandorina.cinemobile.view.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieResponse
import com.pandorina.cinemobile.databinding.FragmentDiscoverBinding
import com.pandorina.cinemobile.util.CinemobileAd
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.adapter.DiscoverListAdapter
import com.pandorina.cinemobile.view.adapter.HorizontalListAdapter
import com.pandorina.cinemobile.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate, null) {

    private val viewPagerAdapterDiscover = DiscoverListAdapter()
    private val popularAdapter = HorizontalListAdapter()
    private val topRatedAdapter = HorizontalListAdapter()
    private val nowPlayingAdapter = HorizontalListAdapter()
    private val upcomingAdapter = HorizontalListAdapter()

    private val moviesViewModel: MoviesViewModel by viewModels()

    private var currentViewPagerItem: Int? = 0

    override fun setUpViews() {
        Util.setActionBarText(requireActivity(), getString(R.string.movies))
        setHasOptionsMenu(true)
        onClickViewAll()
        binding.viewPagerMovie.adapter = viewPagerAdapterDiscover
        setUpRecyclerViews()
        setHeaders()
        binding.adDiscover1.root.loadAd(CinemobileAd.getAdRequest())
        binding.adDiscover2.root.loadAd(CinemobileAd.getAdRequest())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_fragment_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_genres -> {
                binding.root.navigate(R.id.genreListFragment, null, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun onClickViewAll() {
        binding.apply {
            buttonViewAllPopular.root.setOnClickListener {
                it.navigate(R.id.moreFragment, Constant.ARG_TITLE, getString(R.string.popular))
            }

            buttonViewTopRated.root.setOnClickListener {
                it.navigate(R.id.moreFragment, Constant.ARG_TITLE, getString(R.string.top_rated))
            }

            buttonViewNowPlaying.root.setOnClickListener {
                it.navigate(R.id.moreFragment, Constant.ARG_TITLE, getString(R.string.now_playing))
            }

            buttonViewUpcoming.root.setOnClickListener {
                it.navigate(R.id.moreFragment, Constant.ARG_TITLE, getString(R.string.upcoming))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        currentViewPagerItem = binding.viewPagerMovie.currentItem
    }

    override fun onResume() {
        super.onResume()
        currentViewPagerItem?.let { binding.viewPagerMovie.setCurrentItem(it, false) }
    }

    private fun setUpRecyclerViews() {
        setUpMovieRecyclerView(binding.recyclerViewPopularMovies, popularAdapter)
        setUpMovieRecyclerView(binding.recyclerViewTopRatedMovies, topRatedAdapter)
        setUpMovieRecyclerView(binding.recyclerViewNowPlayingMovies, nowPlayingAdapter)
        setUpMovieRecyclerView(binding.recyclerViewUpcomingMovies, upcomingAdapter)
    }

    private fun setUpMovieRecyclerView(
        recyclerView: RecyclerView,
        currentAdapter: HorizontalListAdapter
    ) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = currentAdapter
        }
    }

    private fun setHeaders() {
        binding.apply {
            textViewHeaderPopularMovies.text = getText(R.string.popular)
            textViewHeaderTopRatedMovies.text = getText(R.string.top_rated)
            textViewHeaderNowPlayingMovies.text = getText(R.string.now_playing)
            textViewHeaderUpcomingMovies.text = getText(R.string.upcoming)
        }
    }

    override fun observeData() {
        val shimmerViewPager = binding.shimmerViewPager.root
        moviesViewModel.apply {
            discoverMovieList.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data?.results?.let { viewPagerAdapterDiscover.submitList(it) }
                        shimmerViewPager.isVisible = false
                    }
                    is NetworkResult.Error -> shimmerViewPager.isVisible = true
                    is NetworkResult.Loading -> shimmerViewPager.isVisible = true
                }
            })

            getDiscoverMovies()
            getMovieGroup(Constant.PATH_POPULAR, moviesViewModel.popularMovieList)
            getMovieGroup(Constant.PATH_TOP_RATED, moviesViewModel.topRatedMovieList)
            getMovieGroup(Constant.PATH_NOW_PLAYING, moviesViewModel.nowPlayingMovieList)
            getMovieGroup(Constant.PATH_UPCOMING, moviesViewModel.upcomingMovieList)

            observeMovieGroup(popularMovieList, popularAdapter, binding.shimmerPopular.root)
            observeMovieGroup(topRatedMovieList, topRatedAdapter, binding.shimmerTopRated.root)
            observeMovieGroup(
                nowPlayingMovieList,
                nowPlayingAdapter,
                binding.shimmerNowPlaying.root
            )
            observeMovieGroup(upcomingMovieList, upcomingAdapter, binding.shimmerUpcoming.root)
        }
    }

    private fun observeMovieGroup(
        list: MutableLiveData<NetworkResult<MovieResponse>>,
        adapter: HorizontalListAdapter,
        shimmer: LinearLayout
    ) {
        list.observe(viewLifecycleOwner, { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data?.results?.let { adapter.submitList(it) }
                    shimmer.isVisible = false
                }
                is NetworkResult.Error -> shimmer.isVisible = true
                is NetworkResult.Loading -> shimmer.isVisible = true
            }
        })
    }
}