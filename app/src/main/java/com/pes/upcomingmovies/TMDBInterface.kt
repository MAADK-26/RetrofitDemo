package com.pes.upcomingmovies

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TMDBInterface {

    // suspend - enforcing the call from coroutine - thread

    // Url - https://api.themoviedb.org/3/movie/upcoming?api_key=key
    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        key: String
    ): UpcomingMovieList

    companion object {
        val BASE_URL = "https://api.themoviedb.org/"

        private var instance: TMDBInterface? = null

        // Singleton pattern
        fun getInstance(): TMDBInterface{
            if(instance == null){
                // retrofit object
                val retro = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                instance = retro.create(TMDBInterface::class.java)
            }
            return instance!!
        }

    }

}