package com.pandorina.cinemobile.view.fragment.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.view.adapter.CastAdapter
import com.pandorina.cinemobile.databinding.FragmentCastBinding
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.viewmodel.CastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastFragment: Fragment(R.layout.fragment_cast){
    private var _binding: FragmentCastBinding? = null
    private val binding get() = _binding!!

    private val castAdapter = CastAdapter(arrayListOf())
    private val viewModel: CastViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCastBinding.bind(view)

        (arguments?.get("arg_movie_detail") as MovieDetail).id?.let {
            setUpRecyclerView()
            getData(it)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCast.apply {
            setHasFixedSize(true)
            adapter = castAdapter
        }
    }

    private fun getData(movieId: Int){
        viewModel.getCast(movieId, Constant.PATH_MOVIE)
        viewModel.castList.observe(viewLifecycleOwner, Observer {
            castAdapter.update(it.cast)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}