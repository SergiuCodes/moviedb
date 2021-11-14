package com.example.sergiumoviedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sergiumoviedb.adapters.MovieRecyclerView;
import com.example.sergiumoviedb.adapters.OnMovieListener;
import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.requests.Service;
import com.example.sergiumoviedb.response.MovieSearchResponse;
import com.example.sergiumoviedb.utils.Credentials;
import com.example.sergiumoviedb.utils.MovieApi;
import com.example.sergiumoviedb.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// RAMAS LA 04:15

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {


    //RecyclerView

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;


    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;

    //Toolbar

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RecyclerView

        recyclerView = findViewById(R.id.recyclerView);

        //Instantiate ViewModel

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        configureRecyclerView();
        setupSearchView();
        ObservePopularMovies();


        //Calling observer
        observeDataChanges();

        //Listing popular movies

        movieListViewModel.searchMoviePopular(1);


    }

    private void ObservePopularMovies() {

        movieListViewModel.getPopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // TO DO: Observing for data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in the log
                        Log.v("TAG", "onChanged: " + movieModel.getTitle());
                        movieRecyclerViewAdapter.setmMovies(movieModels);
                    }
                }

            }
        });


    }


    private void getRetrofitResponse() {

        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(Credentials.API_KEY, "Action", 2);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {
                    Log.v("TAG", "the response" + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
                        Log.v("TAG", "The title is: " + movie.getTitle());
                    }
                } else {
                    try {
                        Log.v("TAG", "Error" + response.errorBody().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });


    }

    private void getRetrofitResponseById() {

        MovieApi movieApi = Service.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(550, Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.v("TAG", "The response: " + movie.getTitle());
                } else {
                    try {
                        Log.v("TAG", "Error: " + response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

    }

    //OBSERVER method (Data change observer)

    private void observeDataChanges() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // TO DO: Observing for data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in the log
                        Log.v("TAG", "onChanged: " + movieModel.getTitle());
                        movieRecyclerViewAdapter.setmMovies(movieModels);
                    }
                }

            }
        });
    }


    //Recycler view and adding data to it

    private void configureRecyclerView() {

        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //RecyclerView pagination
        //Loading other pages not just page 1
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    // Here we are displaying next search results on next page
                    movieListViewModel.searchNextPage();
                }

            }
        });


    }

    @Override
    public void onMovieClick(int position) {

        //Using movie id to get details

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);


    }

    @Override
    public void onCategoryClick(String category) {

    }

    //Get data from api and query it to get the results

    private void setupSearchView() {
        final SearchView search_view = findViewById(R.id.search_view);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search_view.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });


    }
}










