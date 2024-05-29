package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.api.MovieService
import com.example.retrofitexample.data.api.Result
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MovieRepositoryImpl(private val retrofitInstance: RetrofitInstance) : MovieRepository {

    private val movieService: MovieService =
        retrofitInstance.getRetrofitInstance().create(MovieService::class.java)

    override suspend fun getPopularMovies(): Flow<Result<List<MovieResponse>>> {
        return flow {
            val movies = try {
                movieService.getPopularMovies(page = 1, apiKey = "api")
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

}