package com.pandorina.cinemobile.data.remote.paging

import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.service.MovieService
import com.pandorina.cinemobile.util.Constant
import retrofit2.HttpException
import java.io.IOException

class SearchMoviesPagingSource(
    private val services: MovieService,
    private val query: String
) : BasePagingSource<Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: Constant.DEFAULT_STARTING_PAGE_INDEX
            val result = services.getSearchMovies(query, position).results

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
}