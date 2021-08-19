package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.MoreMoviesAdapter
import com.pandorina.cinemobile.view.adapter.MoviesByGenresAdapter
import com.pandorina.cinemobile.databinding.FragmentMoviesByGenresBinding
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.viewmodel.MoviesByGenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesByGenresFragment : Fragment(R.layout.fragment_movies_by_genres) {
    var _binding: FragmentMoviesByGenresBinding? = null
    val binding get() = _binding!!

    val args: MoviesByGenresFragmentArgs by navArgs()
    val mAdapter = MoviesByGenresAdapter()
    val viewModel: MoviesByGenresViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMoviesByGenresBinding.bind(view)

        args.genreArg?.let { genre ->
            activity?.let {
                setActionBarText(it, genre.name)
            }
        }

        setUpRecyclerView()
        getData()
    }

    private fun getData(){
        lifecycleScope.launch{
            viewModel.getMoviesByGenres(args.genreArg!!.id).collect {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewMoviesByGenres.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}