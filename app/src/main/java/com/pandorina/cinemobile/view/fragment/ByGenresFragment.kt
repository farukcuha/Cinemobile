package com.pandorina.cinemobile.view.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.databinding.FragmentByGenresBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.view.adapter.VerticalPagingAdapter
import com.pandorina.cinemobile.viewmodel.ByGenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ByGenresFragment :
    BaseFragment<FragmentByGenresBinding>(FragmentByGenresBinding::inflate, Constant.ARG_GENRE) {
    private val genreAdapter = VerticalPagingAdapter()
    val viewModel: ByGenresViewModel by viewModels()

    override fun observeData() {
        val genre = (argument as Genre)
        setActionBarText(requireActivity(), genre.name)
        lifecycleScope.launch {
            viewModel.getMoviesByGenres(genre.id).collect {
                genreAdapter.submitData(it)
            }
        }
    }

    override fun setUpViews() {
        binding.recyclerViewMoviesByGenres.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = genreAdapter
        }
    }
}