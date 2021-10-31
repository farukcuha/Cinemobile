package com.pandorina.cinemobile.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentCollectionBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Constant.REMOTE_ERROR
import com.pandorina.cinemobile.view.adapter.VerticalListAdapter
import com.pandorina.cinemobile.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>(
    FragmentCollectionBinding::inflate,
    Constant.ARG_MOVIE_DETAIL
) {
    private val viewModel by viewModels<CollectionViewModel>()
    private val collectionAdapter = VerticalListAdapter()

    override fun observeData() {
        (argument as MovieDetail).belongs_to_collection?.id?.let { viewModel.getMovieCollection(it) }
        viewModel.movieCollectionList.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    collectionAdapter.submitList(it.data?.parts)
                }
                is NetworkResult.Error -> {
                    Log.e(REMOTE_ERROR, it.message.toString())
                }
                else -> return@observe
            }
        }
    }

    override fun setUpViews() {
        binding.recyclerViewCollection.apply {
            adapter = collectionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerViewCollection.requestLayout()
    }
}