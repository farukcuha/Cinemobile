package com.pandorina.cinemobile.view.fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.remote.NetworkResult
import com.pandorina.cinemobile.data.remote.model.Genre
import com.pandorina.cinemobile.databinding.FragmentHomeBinding
import com.pandorina.cinemobile.databinding.ItemConfigurationBinding
import com.pandorina.cinemobile.databinding.ItemGenreChipBinding
import com.pandorina.cinemobile.util.CinemobileAd
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Constant.REMOTE_ERROR
import com.pandorina.cinemobile.util.Preferences
import com.pandorina.cinemobile.util.Util
import com.pandorina.cinemobile.util.Util.navigate
import com.pandorina.cinemobile.view.adapter.TrendingAdapter
import com.pandorina.cinemobile.view.dialog.InfoDialogFragment
import com.pandorina.cinemobile.view.dialog.LoadingDialog
import com.pandorina.cinemobile.view.dialog.RecommendedMovieDialog
import com.pandorina.cinemobile.viewmodel.GenresViewModel
import com.pandorina.cinemobile.viewmodel.HomeViewModel
import com.robertlevonyan.views.chip.Chip
import com.thekhaeng.pushdownanim.PushDownAnim
import com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate, null) {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val genreViewModel by viewModels<GenresViewModel>()

    private val trendingAdapter = TrendingAdapter()

    companion object {
        const val YEAR = "year"
        const val LANGUAGE = "language"
        const val YEAR_INDEX = "year_index"
        const val LANGUAGE_INDEX = "language_index"
        const val NAME_GENRE = "name_genre"
        const val ID_GENRE = "id_genre"
        const val INFO_DIALOG = "info_dialog"
        const val EMPTY_DIALOG = "empty_dialog"
        const val RECOMMEND_DIALOG = "recommend_dialog"
        const val MOVIE_DIALOG = "movie_dialog"
        const val MONTH_DAY = "-01-01"
        const val ACTION_COUNT_FOR_AD = "action_count_for_ad"
        const val WAS_INFO_DIALOG_SHOWED = "was_info_dialog_showed"
    }

    override fun observeData() {
        homeViewModel.getTrendingMovies()
        homeViewModel.trendingMovieResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    trendingAdapter.submitList(it.data?.results)
                    binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
                    binding.rvTrending.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                    binding.rvTrending.visibility = View.INVISIBLE
                }
                is NetworkResult.Error -> Log.e(REMOTE_ERROR, it.message.toString())
            }
        }

        genreViewModel.genreResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        PushDownAnim.setPushDownAnimTo(binding.btnRandom2)
                            .setScale(MODE_SCALE, 0.85F)
                            .setOnClickListener {
                                recommendMovie()
                            }
                            .setOnLongClickListener {
                                getBottomSheet(response.genres).show()
                                true
                            }
                    }
                }
                is NetworkResult.Error -> Log.e(REMOTE_ERROR, it.message.toString())
                else -> return@observe
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info -> InfoDialogFragment(R.string.recommend_info).show(
                childFragmentManager,
                INFO_DIALOG
            )
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.rvTrending.layoutManager?.scrollToPosition(trendingAdapter.currentScrollPosition)
    }

    override fun setUpViews() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.trending_today)
        setHasOptionsMenu(true)

        binding.rvTrending.apply {
            adapter = trendingAdapter
            setHasFixedSize(true)
        }

        Preferences(requireActivity()).sharedPreferences.getBoolean(WAS_INFO_DIALOG_SHOWED, false)
            .let {
                if (!it) {
                    InfoDialogFragment(R.string.recommend_info).show(
                        childFragmentManager,
                        INFO_DIALOG
                    )
                    Preferences(requireActivity()).edit().putBoolean(WAS_INFO_DIALOG_SHOWED, true)
                        .commit()
                }
            }
    }

    private fun recommendMovie() {
        val loadingDialog = LoadingDialog()
        val emptyDialog = InfoDialogFragment(R.string.movie_empty_text)
        loadingDialog.show(childFragmentManager, RECOMMEND_DIALOG)

        val durations = listOf<Long>(1000, 1150, 1250, 1350, 1450)
        var genres: String? = ""
        var languageCode: String
        var primaryReleaseYear: String? = "1950$MONTH_DAY"
        var seconderReleaseYear: String? = "2021$MONTH_DAY"

        Preferences(requireActivity()).sharedPreferences.apply {
            getInt(LANGUAGE_INDEX, 0).let {
                languageCode = Util.getLanguageCodeByIndex(it)
            }
            getString(YEAR, null)?.let {
                if (it.length == 11) {
                    primaryReleaseYear = it.substring(0, 4) + MONTH_DAY
                    seconderReleaseYear = it.substring(7, 11) + MONTH_DAY
                } else if (it == getString(R.string.all_times)) {
                    primaryReleaseYear = "1950$MONTH_DAY"
                    seconderReleaseYear = "2021$MONTH_DAY"
                } else {
                    primaryReleaseYear = "$it-01-01"
                    seconderReleaseYear = "$it-12-31"
                }
            }
            getStringSet(ID_GENRE, null)?.let { set ->
                set.forEach {
                    genres += "$it, "
                }
            }
        }

        homeViewModel.getRandomMovies(genres, 1, primaryReleaseYear, seconderReleaseYear, languageCode)
        Handler(Looper.getMainLooper()).postDelayed({
            homeViewModel.randomMovieResponse.value?.let {
                when(it){
                    is NetworkResult.Success -> {
                        if (it.data?.results?.isNotEmpty() == true) {
                            val totalPages = it.data.total_pages
                            val totalResults = it.data.total_results

                            val randomPage = (1..totalPages).random()
                            homeViewModel.getRandomMovies(
                                genres,
                                randomPage,
                                primaryReleaseYear,
                                seconderReleaseYear,
                                languageCode
                            )
                            Handler(Looper.getMainLooper()).postDelayed({
                                homeViewModel.randomMovieResponse.value.let { result ->
                                    when (result) {
                                        is NetworkResult.Success -> {
                                            val randomIndex: Int = if (randomPage != totalPages){
                                                (0..19).random()
                                            } else {
                                                (0 until totalResults % (totalPages * 20)).random()
                                            }
                                            val movie = result.data!!.results[randomIndex]

                                            val navigateToMovieDetail: () -> Unit = {
                                                binding.root.navigate(
                                                    R.id.detailFragment,
                                                    Constant.ARG_MOVIE,
                                                    movie
                                                )
                                            }
                                            RecommendedMovieDialog(movie, navigateToMovieDetail,
                                                { recommendMovie() }).show(
                                                childFragmentManager,
                                                MOVIE_DIALOG
                                            )
                                            loadingDialog.dismiss()
                                        }
                                        is NetworkResult.Error -> {
                                            Log.e(REMOTE_ERROR, it.message.toString())
                                            loadingDialog.dismiss()
                                            emptyDialog.show(parentFragmentManager, EMPTY_DIALOG)
                                        }
                                        else -> return@let
                                    }
                                }
                            }, durations.random())
                        } else {
                            loadingDialog.dismiss()
                            emptyDialog.show(parentFragmentManager, EMPTY_DIALOG)
                        }
                    }
                    is NetworkResult.Error -> {
                        loadingDialog.dismiss()
                        emptyDialog.show(parentFragmentManager, EMPTY_DIALOG)
                        Log.d(REMOTE_ERROR, it.message.toString())
                    }
                    else -> return@let
                }
            }
        }, durations.random())
    }

    private fun getBottomSheet(list: List<Genre>): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding =
            ItemConfigurationBinding.inflate(LayoutInflater.from(requireContext()))
        val cinemobilePreferences = Preferences(requireActivity()).sharedPreferences
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        createGenreChipGroup(list, bottomSheetBinding)

        bottomSheetBinding.apply {
            spnYears.selectItemByIndex(cinemobilePreferences.getInt(YEAR_INDEX, 0))
            spnLanguages.selectItemByIndex(cinemobilePreferences.getInt(LANGUAGE_INDEX, 0))
        }

        bottomSheetBinding.btnSubmit.setOnClickListener {
            val genreStringSet = mutableSetOf<String>()
            val genreIdSet = mutableSetOf<String>()
            for (c in bottomSheetBinding.cgGenres.children){
                val chip = c as Chip
                if (chip.chipSelected){
                    val strGenre = chip.getText().toString()
                    genreStringSet.add(strGenre)
                    genreViewModel.genreResponse.value?.data?.genres?.forEach {
                        if(strGenre == it.name){
                            genreIdSet.add(it.id.toString())
                        }
                        return@forEach
                    }
                }
            }
            Preferences(requireActivity()).edit().apply{
                putStringSet(NAME_GENRE, genreStringSet)
                putStringSet(ID_GENRE, genreIdSet)
                putString(YEAR, bottomSheetBinding.spnYears.text.toString())
                putInt(YEAR_INDEX, bottomSheetBinding.spnYears.selectedIndex)
                putString(LANGUAGE, bottomSheetBinding.spnLanguages.text.toString())
                putInt(LANGUAGE_INDEX, bottomSheetBinding.spnLanguages.selectedIndex)
            }.apply()
            bottomSheetDialog.dismiss()
        }

        return bottomSheetDialog
    }

    private fun createGenreChip(genre: Genre, isChecked: Boolean): Chip {
        val binding = ItemGenreChipBinding.inflate(LayoutInflater.from(requireContext()))
        val chip = binding.root
        chip.setText(genre.name)
        chip.chipSelected = isChecked
        return chip
    }

    private fun createGenreChipGroup(list: List<Genre>, binding: ItemConfigurationBinding) {
        val genrePreferences =
            Preferences(requireActivity()).sharedPreferences.getStringSet(NAME_GENRE, null)
        for (genre in list) {
            genrePreferences?.let { set ->
                if (set.contains(genre.name)) binding.cgGenres.addView(createGenreChip(genre, true))
                else binding.cgGenres.addView(createGenreChip(genre, false))
            } ?: run {
                binding.cgGenres.addView(createGenreChip(genre, false))
            }
        }
    }
}