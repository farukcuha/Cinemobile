package com.pandorina.cinemobile.view.fragment.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.SwipeToDeleteCallback
import com.pandorina.cinemobile.databinding.FragmentFavoriteMoviesBinding
import com.pandorina.cinemobile.view.adapter.FavoriteMoviesAdapter
import com.pandorina.cinemobile.viewmodel.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(R.layout.fragment_favorite_movies) {
   private var _binding: FragmentFavoriteMoviesBinding? = null
   val binding get() = _binding!!

    private val favoriteMoviesAdapter = FavoriteMoviesAdapter()

    private val viewModel by viewModels<FavoriteMoviesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFavoriteMoviesBinding.bind(view)


        setUpRecyclerView()
        observeFavoriteMovieList()
    }

    private fun observeFavoriteMovieList(){
        viewModel.favoriteMoviesList.observe(viewLifecycleOwner){
            favoriteMoviesAdapter.submitList(it)

            Log.d("data", it.toString())
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteMoviesAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val favoriteMovieItem = favoriteMoviesAdapter.getFavoriteMovieItem(viewHolder.absoluteAdapterPosition)
                viewModel.deleteFavoriteMovie(favoriteMovieItem)
                binding.root.requestLayout()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFavoriteMovies)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}