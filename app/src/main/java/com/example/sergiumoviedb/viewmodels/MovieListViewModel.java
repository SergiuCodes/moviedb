package com.example.sergiumoviedb.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.repositories.MovieRepository;

import java.util.List;


//This class is used for ViewModel

public class MovieListViewModel extends ViewModel {


    //Instance of the repository
    private MovieRepository movieRepository;


    //Constructor

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    //GETTER
    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();

    }

    public LiveData<List<MovieModel>> getPopular() {
        return movieRepository.getPopular();

    }

    //3. Calling method in ViewModel from Repository

    public void searchMovieApi(String query, int pageNumber) {
        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePopular(int pageNumber) {
        movieRepository.searchMoviePopular(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();

    }
}
