package com.pandorina.cinemobile.view.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.databinding.FragmentRecommendedMovieDialogBinding
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.viewmodel.GenresViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
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

        PushDownAnim.setPushDownAnimTo(binding.btnRandom)
            .setScale(PushDownAnim.MODE_SCALE, 0.8F)
            .setOnClickListener {
                dialog?.dismiss()
                recommendMovie.invoke()
            }

        binding.ivPoster.setOnClickListener { navigate.invoke() }

        binding.apply {
            ivPoster.loadImage(movie.posterPathUrl)
            tvTitle.text = movie.title
            tvYear.text = movie.release_date
            var strGenres = ""
            genresViewModel.genreResponse.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        for (id in movie.genre_ids!!) {
                            result.data?.genres?.map {
                                if (id == it.id) {
                                    strGenres += it.name + " - "
                                }
                                return@map
                            }
                        }

                        tvGenres.text = if (strGenres.isNotEmpty()) {
                            strGenres.substring(0, strGenres.length - 2)
                        } else {
                            ""
                        }
                    }
                }
            }

        }
    }
}