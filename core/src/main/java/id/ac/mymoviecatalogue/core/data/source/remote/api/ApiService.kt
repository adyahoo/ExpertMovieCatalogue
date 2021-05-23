package id.ac.mymoviecatalogue.core.data.source.remote.api

import id.ac.mymoviecatalogue.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/now_playing")
    suspend fun getListMovies(@Query("api_key") apiKey: String): MoviesResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse
}