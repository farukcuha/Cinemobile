package com.pandorina.cinemobile.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandorina.cinemobile.TMDBApi
import com.pandorina.cinemobile.model.Movie
import com.pandorina.cinemobile.util.Constant.DEFAULT_STARTING_PAGE_INDEX
class MoreMoviesPagingSource(
    private val api: TMDBApi,
    private var type: String
        ) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val results = getResponseByType(position)

            LoadResult.Page(
                data = results,
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getResponseByType(position: Int): ArrayList<Movie> =
            when(type){
                "Popular" -> api.getPopularMovies(position).body()!!.results
                "Top Rated" -> api.getTopRatedMovies(position).body()!!.results
                "Now Playing" -> api.getNowPlayingMovies(position).body()!!.results
                "Upcoming" -> api.getUpcomingMovies(position).body()!!.results
                else -> arrayListOf()
            }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

