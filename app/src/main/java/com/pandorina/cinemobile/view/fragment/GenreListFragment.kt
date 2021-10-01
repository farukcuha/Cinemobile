package com.pandorina.cinemobile.view.fragment

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
        viewModel.genreResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.apply {
                            submitList(it.genres)
                        }
                    }
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
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