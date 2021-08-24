package com.pandorina.cinemobile.view.fragment.movie

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentMovieSearchBinding
import com.pandorina.cinemobile.view.adapter.SearchMoviesAdapter
import com.pandorina.cinemobile.viewmodel.SearchMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.pandorina.cinemobile.util.Util
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment(R.layout.fragment_movie_search), SearchView.OnQueryTextListener {
    private var _binding: FragmentMovieSearchBinding? = null
    val binding get() =  _binding!!

    private val viewModel: SearchMoviesViewModel by viewModels()
    private val searchMoviesAdapter = SearchMoviesAdapter()

    var searchView: SearchView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieSearchBinding.bind(view)

        setUpRecyclerView()
        setHasOptionsMenu(true)
        requireActivity().actionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            Util.Keyboard(requireContext()).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_fragment_action_bar, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView?.let {
            it.queryHint = getString(R.string.search_movie)
            it.setOnQueryTextListener(this)
            it.maxWidth = Int.MAX_VALUE
            it.setIconifiedByDefault(false)
            it.requestFocusFromTouch()
        }.also {
            searchMovies()
        }

    }

    override fun onPause() {
        super.onPause()
        searchView?.let { Util.Keyboard(requireContext()).hide(it.windowToken) }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewSearchResults.apply {
            adapter = searchMoviesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun searchMovies(){
        viewModel.apply {
            resultPagingData.observe(viewLifecycleOwner){ resultPagingData ->
                searchMoviesAdapter.submitData(viewLifecycleOwner.lifecycle, resultPagingData)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            viewModel.searchMovies(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}