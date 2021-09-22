package com.pandorina.cinemobile.view.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.util.SwipeToDeleteCallback
import com.pandorina.cinemobile.databinding.FragmentFavoritesBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.adapter.FavoriteListAdapter
import com.pandorina.cinemobile.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate, null) {
    private val favoriteMoviesAdapter = FavoriteListAdapter()
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun observeData() {
        Util.setActionBarText(requireActivity(), getString(R.string.favorite))
        viewModel.favoriteMoviesList.observe(viewLifecycleOwner) {
            favoriteMoviesAdapter.submitList(it)
        }
    }

    override fun setUpViews() {
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteMoviesAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val favoriteMovieItem =
                    favoriteMoviesAdapter.getFavoriteMovieItem(viewHolder.absoluteAdapterPosition)
                viewModel.deleteFavoriteMovie(favoriteMovieItem)
                binding.root.requestLayout()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.root)
    }
}