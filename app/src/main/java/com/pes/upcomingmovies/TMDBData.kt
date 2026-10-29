package com.pes.upcomingmovies

data class UpcomingMovieList(
    val results: List<Movie>
)

data class Movie (
    val id: Long,
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val original_language: String?
)