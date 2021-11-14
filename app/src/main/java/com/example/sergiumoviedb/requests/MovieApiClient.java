package com.example.sergiumoviedb.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sergiumoviedb.AppExecutors;
import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.response.MovieSearchResponse;
import com.example.sergiumoviedb.utils.Credentials;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LIVE Data for search
    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    //Making global runnable request

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    // LIVE data for popular movies list

    private MutableLiveData<List<MovieModel>> mMoviesPopular;

    //Making global runnable request for popular

    private RetrieveMoviesRunnablePopular retrieveMoviesRunnablePopular;

    public static MovieApiClient getInstance() {

        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }



    public LiveData<List<MovieModel>> getMoviesPopular() {
        return mMoviesPopular;
    }



    //Retrieve data from API
    //1. Method that we will call in Repository - ViewModel - API

    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        //Setting a timeout or cancelling retrofit call low memory or no connection for egs. (set to 3 seconds)

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {


                myHandler.cancel(true);


            }
        }, 3000, TimeUnit.MILLISECONDS);

    }

    public void searchMoviesPopular(int pageNumber) {

        if (retrieveMoviesRunnablePopular != null) {
            retrieveMoviesRunnablePopular = null;
        }

        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePopular(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePopular);

        //Setting a timeout or cancelling retrofit call low memory or no connection for egs. (set to 3 seconds)

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {


                myHandler2.cancel(true);


            }
        }, 1000, TimeUnit.MILLISECONDS);

    }

    //Retrieving data from RestAPI by runnable class
    //We have 2 types of Queries: ID search % search movies query (list)

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Getting the response objects from network
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMovies.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("TAG", "Error generated: " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        // Search Method/query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query, pageNumber
            );
        }

        private void cancelRequest() {
            Log.v("TAG", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnablePopular implements Runnable {


        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePopular(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Getting the response objects from network
            try {
                Response response2 = getPopular(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMoviesPopular.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }
                } else {
                    String error = response2.errorBody().string();
                    Log.v("TAG", "Error generated: " + error);
                    mMoviesPopular.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }

        }

        // Search Method/query
        private Call<MovieSearchResponse> getPopular(int pageNumber) {
            return Service.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest() {
            Log.v("TAG", "Cancelling Search Request");
            cancelRequest = true;
        }
    }
}






