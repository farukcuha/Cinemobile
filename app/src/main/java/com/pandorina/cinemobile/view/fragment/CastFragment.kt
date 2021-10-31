package com.pandorina.cinemobile.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentCastBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Constant.REMOTE_ERROR
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
                    Log.e(REMOTE_ERROR, it.message.toString())
                }
                else -> return@observe
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
        binding.recyclerViewCast.requestLayout()
    }
}