package com.pandorina.cinemobile.data.remote.paging

import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.service.MovieService
import com.pandorina.cinemobile.util.Constant
import retrofit2.HttpException

class MovieByGenresPagingSource(
    private val services: MovieService,
    private var genreId: Int
) : BasePagingSource<Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: Constant.DEFAULT_STARTING_PAGE_INDEX
            val results = services.getDiscoverMovies(withGenres = genreId.toString(), page = position).body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if (position == Constant.DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}