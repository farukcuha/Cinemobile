package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentMovieVideosBinding

class MovieVideosFragment: Fragment(R.layout.fragment_movie_videos)  {
    var _binding: FragmentMovieVideosBinding? = null
    val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieVideosBinding.bind(view)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}