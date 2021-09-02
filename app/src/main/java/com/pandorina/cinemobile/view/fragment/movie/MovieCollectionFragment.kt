package com.pandorina.cinemobile.view.fragment.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentMovieCollectionBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.MovieCollectionAdapter
import com.pandorina.cinemobile.viewmodel.MovieCollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieCollectionFragment: Fragment(R.layout.fragment_movie_collection) {
    private var _binding: FragmentMovieCollectionBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<MovieCollectionViewModel>()

    private val collectionAdapter = MovieCollectionAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieCollectionBinding.bind(view)

        val movieDetail = (arguments?.get("arg_movie_detail") as MovieDetail)
        movieDetail.belongs_to_collection?.id?.let {
            viewModel.collectionId.value = it
        }

        binding.recyclerViewCollection.apply {
            adapter = collectionAdapter
            setHasFixedSize(true)
        }

        viewModel.getMovieCollection.observe(viewLifecycleOwner){
            if (it != null) {
                collectionAdapter.submitList(it.parts)
                binding.textViewCollectionTitle.text = it.name
                Log.d("data", it.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}