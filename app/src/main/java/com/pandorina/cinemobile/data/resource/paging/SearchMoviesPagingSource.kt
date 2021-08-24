package com.pandorina.cinemobile.data.resource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandorina.cinemobile.data.model.Movie
import com.pandorina.cinemobile.data.resource.remote.TMDBApi
import com.pandorina.cinemobile.util.Constant
import retrofit2.HttpException
import java.io.IOException

class SearchMoviesPagingSource(
        private val api: TMDBApi,
        private val query: String
): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: Constant.DEFAULT_STARTING_PAGE_INDEX
            val result = api.getSearchMovies(query, position).results

            LoadResult.Page(
                    data = result,
                    nextKey = if (result.isEmpty()) null else position + 1,
                    prevKey = if (position == Constant.DEFAULT_STARTING_PAGE_INDEX) null else 1
            )
        } catch (e: HttpException){
            LoadResult.Error(e)
        } catch (e: IOException){
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