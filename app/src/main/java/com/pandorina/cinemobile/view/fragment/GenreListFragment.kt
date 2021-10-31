package com.pandorina.cinemobile.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.databinding.FragmentGenreListBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.view.adapter.GenreListAdapter
import com.pandorina.cinemobile.viewmodel.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment :
    BaseFragment<FragmentGenreListBinding>(FragmentGenreListBinding::inflate, null) {
    private val viewModel by viewModels<GenresViewModel>()
    private val mAdapter = GenreListAdapter()

    override fun observeData() {
        viewModel.genreResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    mAdapter.submitList(it.data?.genres)
                }
                is NetworkResult.Error -> Log.e(Constant.REMOTE_ERROR, it.message.toString())
                else -> return@observe
            }
        }
    }

    override fun setUpViews() {
        activity?.let { setActionBarText(it, getString(R.string.genres)) }
        binding.root.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}