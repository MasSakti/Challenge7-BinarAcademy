package com.mutawalli.challenge7.network

import com.mutawalli.challenge7.data.api.MovieResponse
import com.mutawalli.challenge7.data.api.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    companion object {
        private const val TOKEN = "0fbaf8c27d542bc99bfc67fb877e3906"
    }

    @GET(EndPoint.Popular.urlPopular)
    suspend fun getPopular(
        @Query("api_key") api: String = TOKEN,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET(EndPoint.Detail.detail)
    suspend fun getDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") api: String = TOKEN
    ): Response<Movies>

}

object EndPoint {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    object Popular {
        const val urlPopular = "movie/popular"
    }

    object Detail {
        const val detail = "movie/{movieId}"
    }
}