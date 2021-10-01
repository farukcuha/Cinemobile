package com.pandorina.cinemobile.view.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.databinding.FragmentSearchBinding
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.view.adapter.QueryListAdapter
import com.pandorina.cinemobile.view.adapter.VerticalPagingAdapter
import com.pandorina.cinemobile.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate, null),
    SearchView.OnQueryTextListener, QueryListAdapter.OnItemClickListener {
    private val viewModel: SearchViewModel by viewModels()
    private val searchMoviesAdapter = VerticalPagingAdapter()
    private val movieQueryAdapter = QueryListAdapter(this)

    var searchView: SearchView? = null
    var currentQuery: String? = null

    override fun setUpViews() {
        setUpRecyclerViews()
        setHasOptionsMenu(true)
        requireActivity().actionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun observeData() {
        observePastMovieQueries()
        binding.textViewClearAllQueries.setOnClickListener {
            viewModel.clearMovieQuery()
        }
    }

    private fun observePastMovieQueries() {
        viewModel.movieQueriesList.observe(viewLifecycleOwner) {
            movieQueryAdapter.submitList(it)
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
            it.requestFocus()
        }.also {
            searchMovies()
        }
        onResume()
    }

    override fun onPause() {
        super.onPause()
        searchView?.let { Util.Keyboard(requireContext()).hide(it.windowToken) }
    }

    override fun onResume() {
        super.onResume()
        searchView?.let { searchView ->
            currentQuery?.let {
                searchView.setQuery(it, false)
            }
        }
    }

    private fun setUpRecyclerViews(){
        binding.recyclerViewSearchResults.apply {
            adapter = searchMoviesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.recyclerViewLastSearchedMovies.apply {
            adapter = movieQueryAdapter
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
            viewModel.insertMovieQuery(MovieQuery(query = it))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (newText == ""){
                clearMovieList()
                binding.apply {
                    rootMovieQueries.isVisible = true
                    recyclerViewSearchResults.isVisible = false
                }
            }else{
                currentQuery = it
                binding.apply {
                    rootMovieQueries.isVisible = false
                    recyclerViewSearchResults.isVisible = true
                }
            }
        }
        return true
    }

    private fun clearMovieList(){
        searchMoviesAdapter.apply {
            submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
        }
    }

    override fun onQueryItem(query: String) {
        searchView?.setQuery(query, true)
    }
}