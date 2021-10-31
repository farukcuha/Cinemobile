package com.pandorina.cinemobile.view.fragment

import android.annotation.SuppressLint
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentOverviewBinding
import com.pandorina.cinemobile.util.CinemobileAd
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.view.adapter.CompanyListAdapter
import com.pandorina.cinemobile.view.adapter.CountryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : BaseFragment<FragmentOverviewBinding>(
    FragmentOverviewBinding::inflate,
    Constant.ARG_MOVIE_DETAIL
) {
    private val companyAdapter = CompanyListAdapter()
    private val countryAdapter = CountryListAdapter()

    var movieDetail: MovieDetail? = null

    override fun observeData() {
        movieDetail = argument as MovieDetail

        movieDetail?.let {
            companyAdapter.submitList(it.production_companies)
            countryAdapter.submitList(it.production_countries)
        }
        setUpViews()
    }

    override fun setUpViews() {
        setUpRecyclerViews()
        setContentText()
        binding.adOverview1.root.loadAd(CinemobileAd.getAdRequest())
        binding.adOverview2.root.loadAd(CinemobileAd.getAdRequest())
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
    private fun setContentText() {
        binding.apply {
            textViewMovieOverviewHeader.text = getString(R.string.overview)
            textViewMovieOverviewProductionCompaniesHeader.text =
                getString(R.string.production_companies)
            textViewMovieOverviewProductionCountriesHeader.text =
                getString(R.string.production_countries)

            imageViewMovieOverviewPosterImage.loadImage(movieDetail?.posterPathUrl)
            textViewMovieOverviewName.text = movieDetail?.title
            textViewMovieOverviewReleaseDateAndRuntime.text =
                "${movieDetail?.release_date} - ${movieDetail?.runtime} ${getString(R.string.minute)}"
            textViewMovieOverviewTagline.text = "\"${movieDetail?.tagline}\""
            textViewMovieOverviewArticle.text = movieDetail?.overview
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}