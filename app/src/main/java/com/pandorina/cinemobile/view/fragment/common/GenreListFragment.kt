package com.pandorina.cinemobile.view.fragment.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.NetworkResult
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.GenreListAdapter
import com.pandorina.cinemobile.databinding.FragmentGenreListBinding
import com.pandorina.cinemobile.model.Genre
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.viewmodel.GenresListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment : Fragment(R.layout.fragment_genre_list) {
    var _binding: FragmentGenreListBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<GenresListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentGenreListBinding.bind(view)
        activity?.let { setActionBarText(it, "Genres") }
        getData()
    }

    private fun setUpRecyclerView(list: List<Genre>){
        binding.recyclerViewGenres.apply {
            setHasFixedSize(true)
            adapter = GenreListAdapter(list)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getData() {
        viewModel.getGenres(Constant.STRING_MOVIE)
        viewModel.genreResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {
                        setUpRecyclerView(it.genres)
                    }
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}