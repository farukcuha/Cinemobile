package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentMovieSimilarBinding
import com.pandorina.cinemobile.view.adapter.MovieCollectionAdapter
import com.pandorina.cinemobile.viewmodel.MovieSimilarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSimilarFragment: Fragment(R.layout.fragment_movie_similar)  {
    private var _binding: FragmentMovieSimilarBinding? = null
    val binding get() = _binding!!

    val similarMoviesAdapter = MovieCollectionAdapter()

    val viewModel by viewModels<MovieSimilarViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieSimilarBinding.bind(view)

        (arguments?.get("arg_movie_detail") as MovieDetail).id?.let {
            viewModel.movieId.value = it
        }

        setUpRecyclerView()
        observeData()
    }

    private fun observeData(){
        viewModel.similarMovieList.observe(viewLifecycleOwner){
            similarMoviesAdapter.submitList(it?.results)
        }

    }
    private fun setUpRecyclerView(){
        binding.recyclerViewSimilarMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = similarMoviesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}