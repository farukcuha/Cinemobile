package com.pandorina.cinemobile.view.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.FragmentRecommendedMovieDialogBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.fragment.HomeFragment
import com.pandorina.cinemobile.viewmodel.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedMovieDialog(
    private val movie: Movie,
    private val navigate: () -> Unit,
    private val recommendMovie: () -> Unit
) : BaseDialogFragment(R.layout.fragment_recommended_movie_dialog) {
    private val genresViewModel by viewModels<GenresViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentRecommendedMovieDialogBinding.bind(view)
        binding.btnDismissDialog.setOnClickListener { dialog?.dismiss() }
        binding.btnRandom.setOnClickListener {
            dialog?.dismiss()
            recommendMovie.invoke()
        }

        binding.ivPoster.setOnClickListener { navigate.invoke() }

        binding.apply {
            ivPoster.loadImage(movie.poster_path_url)
            tvTitle.text = movie.title
            tvYear.text = movie.release_date
            var strGenres = ""
            genresViewModel.genreResponse.observe(viewLifecycleOwner){ result ->
                when(result){
                    is NetworkResult.Success -> {
                        for (id in movie.genre_ids!!) {
                            result.data?.genres?.map {
                                if(id == it.id){
                                    strGenres += it.name + " - "
                                }
                                return@map
                            }
                        }
                        tvGenres.text = strGenres.run {
                            this.substring(0, this.length - 2)
                        }
                    }
                }
            }

        }
    }
}