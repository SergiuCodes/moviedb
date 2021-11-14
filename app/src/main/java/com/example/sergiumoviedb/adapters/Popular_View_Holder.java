package com.example.sergiumoviedb.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sergiumoviedb.R;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMovieListener onMovieListener;
    ImageView imageView2;
    RatingBar ratingBar2;


    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = this.onMovieListener;
        imageView2 = itemView.findViewById(R.id.imageView2);
        ratingBar2 = itemView.findViewById(R.id.ratingBar2);

        itemView.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

    }
}
