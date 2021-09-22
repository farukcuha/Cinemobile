package com.pandorina.cinemobile.view.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentCastBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.CastListAdapter
import com.pandorina.cinemobile.viewmodel.CastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastFragment :
    BaseFragment<FragmentCastBinding>(FragmentCastBinding::inflate, Constant.ARG_MOVIE_DETAIL) {
    private val castAdapter = CastListAdapter()
    private val viewModel: CastViewModel by viewModels()

    override fun observeData() {
        val movieDetail = argument as MovieDetail
        movieDetail.id?.let { viewModel.getCast(it, Constant.PATH_MOVIE) }
        viewModel.castList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        castAdapter.submitList(response.cast)
                    }
                }
                is NetworkResult.Error -> {

                }
            }
        }
    }

    override fun setUpViews() {
        binding.root.apply {
            adapter = castAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}