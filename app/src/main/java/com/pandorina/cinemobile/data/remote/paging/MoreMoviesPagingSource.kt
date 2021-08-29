package com.pandorina.cinemobile.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandorina.cinemobile.data.remote.service.TMDBApi
import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Constant.DEFAULT_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

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
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    private suspend fun getResponseByType(position: Int): ArrayList<Movie> =
            when(type){
                Constant.PATH_POPULAR -> api.getPopularMovies(position).body()!!.results
                Constant.PATH_TOP_RATED -> api.getTopRatedMovies(position).body()!!.results
                Constant.PATH_NOW_PLAYING -> api.getNowPlayingMovies(position).body()!!.results
                Constant.PATH_UPCOMING -> api.getUpcomingMovies(position).body()!!.results
                else -> arrayListOf()
            }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

