package com.pandorina.cinemobile.view.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.cinemobile.data.remote.model.MovieDetail
import com.pandorina.cinemobile.databinding.FragmentVideosBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.VideoListAdapter
import com.pandorina.cinemobile.viewmodel.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideosFragment :
    BaseFragment<FragmentVideosBinding>(FragmentVideosBinding::inflate, Constant.ARG_MOVIE_DETAIL) {
    private val videoAdapter = VideoListAdapter()
    val viewModel by viewModels<VideosViewModel>()

    override fun observeData() {
        (argument as MovieDetail).id?.let {
            viewModel.getVideoList(Constant.PATH_MOVIE, it)
        }
        viewModel.videoList.observe(viewLifecycleOwner) {
            it.data?.results?.let { videoList ->
                videoList.reverse()
                videoAdapter.submitList(videoList)
            }
        }
    }

    override fun setUpViews() {
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = videoAdapter
        }
    }


}