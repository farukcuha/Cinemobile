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
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieOverviewFragment: Fragment(R.layout.fragment_movie_overview) {
    private var _binding: FragmentMovieOverviewBinding? = null
    private val binding get() = _binding!!

    private val companyAdapter = ProductionCompaniesAdapter()
    private val countryAdapter = ProductionCountriesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieOverviewBinding.bind(view)

        val movieDetail = arguments?.get("arg_movie_detail") as MovieDetail

        with(movieDetail){
            setUpRecyclerViews(movieDetail)
            setContentText(movieDetail)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setUpRecyclerViews(movieDetail: MovieDetail) {
        binding.apply {
            recyclerViewMovieOverviewProductionCompanies.apply {
                setHasFixedSize(true)
                adapter = companyAdapter.also { adapter ->
                    movieDetail.production_companies?.let {
                        adapter.list = it
                    }
                }
            }
            recyclerViewMovieOverviewProductionCountries.apply {
                setHasFixedSize(true)
                adapter = countryAdapter.also { adapter ->
                    movieDetail.production_countries?.let {
                        adapter.list = it
                    }
                }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}