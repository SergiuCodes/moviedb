package com.example.sergiumoviedb.utils;

import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // SEARCH for movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page);

    // SEARCH WITH ID
    //https://api.themoviedb.org/3/movie/550?api_key=b5331512bf458d0403d035fb76360349
    //550 is the movie_id of Fight Club for example

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    // GET POPULAR MOVIES
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
