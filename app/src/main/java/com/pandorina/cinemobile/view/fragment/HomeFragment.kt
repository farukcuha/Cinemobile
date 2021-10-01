package com.pandorina.cinemobile.view.fragment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pandorina.cinemobile.Preferences
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.databinding.FragmentHomeBinding
import com.pandorina.cinemobile.databinding.ItemConfigurationBinding
import com.pandorina.cinemobile.databinding.ItemGenreChipBinding
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.view.adapter.TrendingAdapter
import com.pandorina.cinemobile.viewmodel.GenresViewModel
import com.pandorina.cinemobile.viewmodel.HomeViewModel
import com.robertlevonyan.views.chip.Chip
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate, null) {
    val homeViewModel by viewModels<HomeViewModel>()
    val genreViewModel by viewModels<GenresViewModel>()

    private val trendingAdapter = TrendingAdapter()

    companion object{
        const val YEAR = "year"
        const val LANGUAGE = "language"
        const val YEAR_INDEX = "year_index"
        const val LANGUAGE_INDEX = "language_index"
        const val GENRE = "genre"
    }

    override fun observeData() {
        homeViewModel.getTrendingMovies()
        homeViewModel.trendingMovieResponse.observe(viewLifecycleOwner){
            when (it) {
                is NetworkResult.Success -> {
                    trendingAdapter.submitList(it.data?.results)
                    binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
    }

    override fun setUpViews() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.trending_today)
        binding.btnRandom.setOnLongClickListener{
            getBottomSheet().show()
            true
        }

        binding.rvTrending.apply {
            adapter = trendingAdapter
            setHasFixedSize(true)
        }
    }

    private fun getBottomSheet(): BottomSheetDialog{
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = ItemConfigurationBinding.inflate(LayoutInflater.from(requireContext()))
        val cinemobilePreferences = Preferences(requireActivity()).sharedPreferences

        genreViewModel.genreResponse.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    createGenreChipGroup(it.data?.genres, bottomSheetBinding)
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
            }
        }

        bottomSheetBinding.apply {
            spnYears.selectItemByIndex(cinemobilePreferences.getInt(YEAR_INDEX, 0))
            spnLanguages.selectItemByIndex(cinemobilePreferences.getInt(LANGUAGE_INDEX, 0))
        }

        bottomSheetBinding.btnSubmit.setOnClickListener {
            val genreSet = mutableSetOf<String>()
            for (c in bottomSheetBinding.cgGenres.children){
                val chip = c as Chip
                if (chip.chipSelected){
                    genreSet.add(chip.getText().toString())
                }
            }
            Preferences(requireActivity()).edit().apply{
                putStringSet(GENRE, genreSet)
                putString(YEAR, bottomSheetBinding.spnYears.text.toString())
                putInt(YEAR_INDEX, bottomSheetBinding.spnYears.selectedIndex)
                putString(LANGUAGE, bottomSheetBinding.spnLanguages.text.toString())
                putInt(LANGUAGE_INDEX, bottomSheetBinding.spnLanguages.selectedIndex)
            }.apply()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        return bottomSheetDialog
    }

    private fun createGenreChip(genre: Genre, isChecked: Boolean): Chip {
        val binding = ItemGenreChipBinding.inflate(LayoutInflater.from(requireContext()))
        val chip = binding.root
        chip.setText(genre.name)
        chip.chipSelected = isChecked
        return chip
    }

    private fun createGenreChipGroup(list: List<Genre>?, binding: ItemConfigurationBinding){
        val genrePreferences = Preferences(requireActivity()).sharedPreferences.getStringSet(GENRE, null)
        for (genre in list!!){
            genrePreferences?.let {
                if (it.contains(genre.name)) binding.cgGenres.addView(createGenreChip(genre, true))
                else binding.cgGenres.addView(createGenreChip(genre, false))
            }
        }
    }
}