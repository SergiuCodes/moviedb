package com.example.sergiumoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sergiumoviedb.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtDetails, txtDescription;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageView = findViewById(R.id.imageView);
        txtDescription = findViewById(R.id.txtDescription);
        txtDetails = findViewById(R.id.txtDetails);
        ratingBar = findViewById(R.id.ratingBar);

        getDataFromIntent();



    }

    private void getDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            txtDetails.setText(movieModel.getTitle());
            txtDescription.setText(movieModel.getMovie_overview());
            ratingBar.setRating((movieModel.getVote_average())/2);

            Log.v("TAGY", "" + movieModel.getMovie_overview());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" +movieModel.getPoster_path())
                    .into(imageView);
        }
    }
}