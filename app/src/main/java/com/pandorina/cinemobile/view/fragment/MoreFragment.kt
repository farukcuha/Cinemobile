package com.pandorina.cinemobile.view.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.databinding.FragmentMoreBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.adapter.VerticalPagingAdapter
import com.pandorina.cinemobile.viewmodel.MoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment :
    BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate, Constant.ARG_TITLE) {
    private val viewModel: MoreViewModel by viewModels()
    var dataAdapter = VerticalPagingAdapter()

    override fun observeData() {
        Util.getPathByTitle(requireContext(), argument as String)?.let {
            viewModel.getMoreMovieGroup(it)
        }

        lifecycleScope.launch {
            viewModel.currentResult?.collectLatest {
                dataAdapter.submitData(it)
            }
        }
    }

    override fun setUpViews() {
        (activity as AppCompatActivity).supportActionBar?.title = argument as String
        binding.root.apply {
            adapter = dataAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}