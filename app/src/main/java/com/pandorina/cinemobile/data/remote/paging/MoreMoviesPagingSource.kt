package com.pandorina.cinemobile.data.remote.paging

import com.pandorina.cinemobile.data.remote.model.Movie
import com.pandorina.cinemobile.data.remote.service.MovieService
import com.pandorina.cinemobile.util.Constant.DEFAULT_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class MoreMoviesPagingSource(
    private val services: MovieService,
    private var type: String
) : BasePagingSource<Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val results = services.getMovieGroup(type, position).body()?.results

            LoadResult.Page(
                data = results!!,
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}


