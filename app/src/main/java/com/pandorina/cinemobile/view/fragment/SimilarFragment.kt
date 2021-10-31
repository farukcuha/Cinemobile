package com.pandorina.cinemobile.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentSimilarBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.VerticalListAdapter
import com.pandorina.cinemobile.viewmodel.SimilarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimilarFragment : BaseFragment<FragmentSimilarBinding>(
    FragmentSimilarBinding::inflate,
    Constant.ARG_MOVIE_DETAIL
) {
    private val similarMoviesAdapter = VerticalListAdapter()
    val viewModel by viewModels<SimilarViewModel>()

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun setUpViews() {
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = similarMoviesAdapter
        }
    }

    override fun observeData() {
        (argument as MovieDetail).id?.let { viewModel.getSimilarMovies(it) }
        viewModel.similarMovieResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        similarMoviesAdapter.submitList(response.results)
                    }
                }
                is NetworkResult.Error -> Log.e(Constant.REMOTE_ERROR, it.message.toString())
                else -> return@observe
            }
        }
    }


}