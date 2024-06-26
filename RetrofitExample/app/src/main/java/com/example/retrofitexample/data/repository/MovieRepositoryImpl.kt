package com.example.retrofitexample.data.repository

import com.example.retrofitexample.BuildConfig
import com.example.retrofitexample.data.api.MovieService
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.data.model.MovieResponse
import com.example.retrofitexample.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MovieRepositoryImpl(private val retrofitInstance: RetrofitInstance) : MovieRepository {

    private val movieService: MovieService = retrofitInstance.getMovieService()
    override suspend fun getPopularMovies(page: Int): Flow<Result<List<MovieResponse>>> {
        return flow {
            emit(Result.Loading())
            val movies = try {
                movieService.getPopularMovies(
                    page = page, apiKey = BuildConfig.apiKey
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error getting movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error getting movies"))
                return@flow
            }
            emit(Result.Success(movies.results))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieResponse>> {
        return flow {
            emit(Result.Loading())
            val movieDetails = try {
                movieService.getMovieDetails(
                    apiKey = BuildConfig.apiKey, movieId = movieId
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error getting movie Details"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error getting movie Details"))
                return@flow
            }
            emit(Result.Success(movieDetails))
        }
    }
}
