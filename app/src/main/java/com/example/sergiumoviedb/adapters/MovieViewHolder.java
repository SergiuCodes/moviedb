package com.example.sergiumoviedb.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sergiumoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    //Widgets implementation

    TextView movie_title, original_language, movie_releaseDate;
    RatingBar rating_bar;
    ImageView movie_img;

    //Click listener interface

    OnMovieListener onMovieListener;




    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        movie_releaseDate = itemView.findViewById(R.id.movie_releaseDate);
        original_language = itemView.findViewById(R.id.original_language);
        movie_title = itemView.findViewById(R.id.movie_title);
        movie_img = itemView.findViewById(R.id.movie_img);
        rating_bar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
