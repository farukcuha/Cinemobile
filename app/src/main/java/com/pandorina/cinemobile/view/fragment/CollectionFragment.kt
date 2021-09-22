package com.pandorina.cinemobile.view.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentCollectionBinding
import com.pandorina.cinemobile.util.Constant
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
            it.data?.let { collection ->
                collectionAdapter.submitList(collection.parts)
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
        binding.root.requestLayout()
    }
}