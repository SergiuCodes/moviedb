package com.example.sergiumoviedb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.requests.MovieApiClient;
import com.example.sergiumoviedb.utils.MovieApi;

import java.util.List;

public class MovieRepository {

    //This class is acting as repository

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    //Singleton

    public static MovieRepository getInstance() {

        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();


    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular() {
        return movieApiClient.getMoviesPopular();
    }

    //2. Calling searchMovies method in the repository

    public void searchMovieApi(String query, int pageNumber) {


        mQuery = query;
        pageNumber = pageNumber;

        movieApiClient.searchMoviesApi(query, pageNumber);


    }

    public void searchMoviePopular(int pageNumber) {



        pageNumber = pageNumber;

        movieApiClient.searchMoviesPopular(pageNumber);


    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }

}







