package com.example.sergiumoviedb.response;


import com.example.sergiumoviedb.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// This class is for single movie request

public class MovieResponse {

    // First step - Finding the movie object

    @SerializedName("results")

    @Expose
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
