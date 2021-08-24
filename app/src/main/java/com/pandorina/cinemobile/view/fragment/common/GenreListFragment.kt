package com.pandorina.cinemobile.view.fragment.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.GenreListAdapter
import com.pandorina.cinemobile.databinding.FragmentGenreListBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.viewmodel.GenresListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment : Fragment(R.layout.fragment_genre_list) {
    private var _binding: FragmentGenreListBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<GenresListViewModel>()
    lateinit var mAdapter: GenreListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentGenreListBinding.bind(view)
        activity?.let { setActionBarText(it, getString(R.string.genres)) }

        mAdapter = GenreListAdapter(listOf())
        setUpRecyclerView()
        getData()
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewGenres.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getData() {
        viewModel.getGenres(Constant.PATH_MOVIE)
        viewModel.genreResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let { genreResponse ->
                       mAdapter.apply {
                           list = genreResponse.genres
                           notifyDataSetChanged()
                       }
                    }
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    //Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}