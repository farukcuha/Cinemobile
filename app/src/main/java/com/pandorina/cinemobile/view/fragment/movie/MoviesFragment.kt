package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.HorizontalMovieAdapter
import com.pandorina.cinemobile.view.adapter.DiscoverMovieViewPagerAdapter
import com.pandorina.cinemobile.databinding.FragmentMoviesBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment: Fragment(R.layout.fragment_movies) {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapterDiscover: DiscoverMovieViewPagerAdapter
    private lateinit var popularAdapter: HorizontalMovieAdapter
    private lateinit var topRatedAdapter: HorizontalMovieAdapter
    private lateinit var nowPlayingAdapter: HorizontalMovieAdapter
    private lateinit var upcomingAdapter: HorizontalMovieAdapter

    private val moviesViewModel: MoviesViewModel by viewModels()

    var currentViewPagerItem: Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movies)
        setHasOptionsMenu(true)
        setHeaders()
        setUpViewPager()
        setUpRecyclerViews()
        observeLiveData()
        checkLoading()
        onClickViewAll()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_fragment_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when(item.itemId){
                R.id.action_genres -> {
                    val action = MoviesFragmentDirections.actionMoviesFragmentToGenresFragment()
                    action.arguments.putString("production_type", Constant.PATH_MOVIE)
                    findNavController().navigate(action)
                    true
                }
                R.id.action_search -> {
                    val action = MoviesFragmentDirections.actionNavMoviesToMovieSearchFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun onClickViewAll() {
        binding.apply {
            textViewViewAllPopularMovies.root.setOnClickListener(View.OnClickListener {
                navigate(getString(R.string.popular))
            })

            textViewViewAllTopRatedMovies.root.setOnClickListener(View.OnClickListener {
                navigate(getString(R.string.top_rated))
            })

            textViewViewAllNowPlayingMovies.root.setOnClickListener(View.OnClickListener {
                navigate(getString(R.string.now_playing))
            })

            textViewViewAllUpcomingMovies.root.setOnClickListener(View.OnClickListener {
                navigate(getString(R.string.upcoming))
            })
        }
    }

    override fun onPause() {
        super.onPause()
        currentViewPagerItem = binding.viewPagerMovie.currentItem
    }

    override fun onResume() {
        super.onResume()
        currentViewPagerItem?.let { binding.viewPagerMovie.setCurrentItem(it, false) }
    }

    private fun navigate(title: String){
        val action = MoviesFragmentDirections.actionMoviesFragmentToMoreMoviesFragment()
        action.arguments.putString("path", title)
        findNavController().navigate(action)
    }

    private fun checkLoading() {
        moviesViewModel.apply {
            discoverIsLoaded.observe(viewLifecycleOwner, {
                viewPagerAdapterDiscover.isLoaded = it
            })

            popularIsLoaded.observe(viewLifecycleOwner, {
                popularAdapter.isLoaded = it
            })

            topRatedIsLoaded.observe(viewLifecycleOwner, {
                topRatedAdapter.isLoaded = it
            })

            nowPlayingIsLoaded.observe(viewLifecycleOwner, {
                nowPlayingAdapter.isLoaded = it
            })

            upcomingIsLoaded.observe(viewLifecycleOwner, {
                upcomingAdapter.isLoaded = it
            })
        }
    }

    private fun setUpRecyclerViews(){
        popularAdapter = HorizontalMovieAdapter(arrayListOf())
        setUpMovieRecyclerView(binding.recyclerViewPopularMovies, popularAdapter)

        topRatedAdapter = HorizontalMovieAdapter(arrayListOf())
        setUpMovieRecyclerView(binding.recyclerViewTopRatedMovies, topRatedAdapter)

        nowPlayingAdapter = HorizontalMovieAdapter(arrayListOf())
        setUpMovieRecyclerView(binding.recyclerViewNowPlayingMovies, nowPlayingAdapter)

        upcomingAdapter = HorizontalMovieAdapter(arrayListOf())
        setUpMovieRecyclerView(binding.recyclerViewUpcomingMovies, upcomingAdapter)
    }

    private fun setUpMovieRecyclerView(recyclerView: RecyclerView, currentAdapter: HorizontalMovieAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = currentAdapter
        }
    }

    private fun setHeaders() {
        binding.textViewHeaderPopularMovies.root.text = getText(R.string.popular)
        binding.textViewHeaderTopRatedMovies.root.text = getText(R.string.top_rated)
        binding.textViewHeaderNowPlayingMovies.root.text = getText(R.string.now_playing)
        binding.textViewHeaderUpcomingMovies.root.text = getText(R.string.upcoming)
    }

    private fun setUpViewPager() {
        viewPagerAdapterDiscover = DiscoverMovieViewPagerAdapter(arrayListOf())
        binding.viewPagerMovie.adapter = viewPagerAdapterDiscover
    }

    private fun observeLiveData() {
        moviesViewModel.getDiscoverMovies()
        moviesViewModel.getPopularMovies()
        moviesViewModel.getTopRatedMovies()
        moviesViewModel.getNowPlayingMovies()
        moviesViewModel.getUpcomingMovies()

        moviesViewModel.apply {
            discoverList.observe(viewLifecycleOwner, { list ->
                list?.let {
                    viewPagerAdapterDiscover.updateList(it.results)
                }
            })

            popularList.observe(viewLifecycleOwner, { list ->
                list?.let { popularAdapter.updateList(it.results) }
            })

            topRatedList.observe(viewLifecycleOwner, { list ->
                list?.let { topRatedAdapter.updateList(it.results) }

            })

            nowPlayingList.observe(viewLifecycleOwner, { list ->
                list?.let { nowPlayingAdapter.updateList(it.results)}

            })

            upcomingList.observe(viewLifecycleOwner, { list ->
                list?.let { upcomingAdapter.updateList(it.results)}

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}