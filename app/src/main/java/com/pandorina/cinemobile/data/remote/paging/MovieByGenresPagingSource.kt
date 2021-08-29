package com.pandorina.cinemobile.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandorina.cinemobile.data.remote.service.TMDBApi
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.util.Constant

class MovieByGenresPagingSource(
        private val api: TMDBApi,
        private var genreId: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: Constant.DEFAULT_STARTING_PAGE_INDEX
            val results = api.getMoviesByGenres(genreId, position).body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if (position == Constant.DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}