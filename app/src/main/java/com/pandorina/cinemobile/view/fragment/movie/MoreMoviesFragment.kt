package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.MoreMoviesAdapter
import com.pandorina.cinemobile.databinding.FragmentMoreMoviesBinding
import com.pandorina.cinemobile.viewmodel.MoreMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreMoviesFragment : Fragment(R.layout.fragment_more_movies) {
    private var _binding: FragmentMoreMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoreMoviesViewModel by viewModels()
    var dataAdapter = MoreMoviesAdapter()
    private var title: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMoreMoviesBinding.bind(view)

        title = arguments?.getString("path")
        dataAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        (activity as AppCompatActivity).supportActionBar?.title = title

        loadData()
        setUpRecyclerView()
        checkIsLoaded()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewVerticalMovieList.apply {
            adapter = dataAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadData() {
        viewModel.apply {
            when (title) {
                getString(R.string.popular) -> {
                    lifecycleScope.launch{
                        viewModel.getMorePopularMovies().collectLatest {
                            dataAdapter.submitData(it)
                        }
                    }
                }
                getString(R.string.top_rated) -> {
                    lifecycleScope.launch{
                        viewModel.getMoreTopRatedMovies().collectLatest {
                            dataAdapter.submitData(it)
                        }
                    }
                }
                getString(R.string.now_playing) -> {
                    lifecycleScope.launch{
                        viewModel.getMoreNowPlayingMovies().collectLatest {
                            dataAdapter.submitData(it)
                        }
                    }
                }
                getString(R.string.upcoming) -> {
                    lifecycleScope.launch{
                        viewModel.getMoreUpcomingMovies().collectLatest {
                            dataAdapter.submitData(it)
                        }
                    }
                }
            }
        }

    }

    private fun checkIsLoaded() {
        dataAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadState.source.refresh.apply {
                    recyclerViewVerticalMovieList.isVisible = this is LoadState.NotLoading
                    shimmerGroupVertical.root.isVisible =
                        this is LoadState.Loading || this is LoadState.Error
                }
            }
        }
    }


}