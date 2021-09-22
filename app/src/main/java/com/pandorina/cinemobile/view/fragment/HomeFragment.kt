package com.pandorina.cinemobile.view.fragment

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.databinding.FragmentHomeBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.TrendingAdapter
import com.pandorina.cinemobile.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import recycler.coverflow.CoverFlowLayoutManger

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate, null) {
    val viewModel by viewModels<HomeViewModel>()
    private val trendingAdapter = TrendingAdapter()

    override fun observeData() {
        viewModel.getTrendingMovies()
        viewModel.trendingMovieResponse.observe(viewLifecycleOwner){
            when (it) {
                is NetworkResult.Success -> {
                    trendingAdapter.submitList(it.data?.results)
                    binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
    }

    override fun setUpViews() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.trending_today)
        binding.rvTrending.apply {
            adapter = trendingAdapter
            setHasFixedSize(true)
        }
    }
}