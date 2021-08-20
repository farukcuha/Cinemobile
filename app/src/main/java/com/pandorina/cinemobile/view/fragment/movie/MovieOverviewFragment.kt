package com.pandorina.cinemobile.view.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.ProductionCompaniesAdapter
import com.pandorina.cinemobile.view.adapter.ProductionCountriesAdapter
import com.pandorina.cinemobile.databinding.FragmentMovieOverviewBinding
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.data.model.MovieDetail
import com.pandorina.cinemobile.util.loadImage
import com.pandorina.cinemobile.viewmodel.MovieOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieOverviewFragment: Fragment(R.layout.fragment_movie_overview) {
    private var _binding: FragmentMovieOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieOverviewViewModel by viewModels()

    lateinit var companyAdapter: ProductionCompaniesAdapter
    lateinit var countryAdapter: ProductionCountriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieOverviewBinding.bind(view)

        val movie = arguments?.get("movie") as Movie

        companyAdapter = ProductionCompaniesAdapter(arrayListOf())
        countryAdapter = ProductionCountriesAdapter(arrayListOf())

        movie.let {
            setUpRecyclerViews()
            getMovieOverviewData(it.id)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setUpRecyclerViews() {
        binding.apply {
            recyclerViewMovieOverviewProductionCompanies.apply {
                setHasFixedSize(true)
                adapter = companyAdapter
            }
            recyclerViewMovieOverviewProductionCountries.apply {
                setHasFixedSize(true)
                adapter = countryAdapter
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setContentText(movieDetail: MovieDetail) {
        binding.apply {
            textViewMovieOverviewHeader.root.text = getString(R.string.overview)
            textViewMovieOverviewProductionCompaniesHeader.root.text = getString(R.string.production_companies)
            textViewMovieOverviewProductionCountriesHeader.root.text = getString(R.string.production_countries)

            imageViewMovieOverviewPosterImage.loadImage(movieDetail.poster_path_url)
            textViewMovieOverviewName.text = movieDetail.title
            textViewMovieOverviewReleaseDateAndRuntime.text = "${movieDetail.release_date} - ${movieDetail.runtime} ${getString(R.string.minute)}"
            textViewMovieOverviewTagline.text = "\"${movieDetail.tagline}\""
            textViewMovieOverviewArticle.root.text = movieDetail.overview
        }
    }

    private fun getMovieOverviewData(movieId: Int) {
        viewModel.getMovieOverview(movieId)
        viewModel.movieOverview.observe(viewLifecycleOwner, Observer {
            setContentText(it)
            countryAdapter.updateList(it.production_countries)
            companyAdapter.updateList(it.production_companies)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}